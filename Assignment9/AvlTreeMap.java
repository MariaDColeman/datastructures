/*
 * Maria Coleman
 * mcolem31
 */

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Ordered maps implemented as Avl trees.
 *
 * These are balanced.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<? super K>, V>
    implements OrderedMap<K, V> {

    // Each node holds a key (which is what we sort the BST by) as well as a
    // value. We don't need a parent pointer as long as we use recursive code
    // for insert/remove.
    private class Node {
        Node left;
        Node right;
        K key;
        V value;
        int height;

        // Constructor to make node creation easier to read.
        Node(K k, V v) {
            // left and right default to null
            // height should default to 1 for new node
            this.key = k;
            this.value = v;
            this.height = 0;
            //FIX THIS. he makes us use -1 for some reason so
            // maybe make height of single node 0. in all class examples he
            // made single node a height of 1 which stinks for this now.
        }

        // Just for debugging purposes.
        public String toString() {
            return "Node<key: " + this.key
                + "; value: " + this.value
                + "; height: " + this.height
                + ">";
        }
    }

    private Node root;
    private int size;
    private StringBuilder stringBuilder;

    @Override
    public int size() {
        return this.size;
    }

    // private helper method that returns -1 for a null pointer
    // and the content of the height field otherwise.
    private int height(Node n) {
        if (n == null) {
            return -1;
        }
        return n.height;
    }


    // Return node for given key, null if key not present.
    private Node find(K k) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        return this.find(this.root, k);
    }

    private Node find(Node n, K k) {
        if (n == null) {
            return null;
        }
        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            return this.find(n.left, k);
        } else if (cmp > 0) {
            return this.find(n.right, k);
        } else {
            return n;
        }
    }

    @Override
    public boolean has(K k) {
        if (k == null) {
            return false;
        }
        return this.find(k) != null;
    }

    // Return node for given key, throw exception if key not present.
    private Node findForSure(K k) {
        Node n = this.find(k);
        if (n == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }
        return n;
    }

    @Override
    public void put(K k, V v) {
        Node n = this.findForSure(k);
        n.value = v;
    }

    @Override
    public V get(K k) {
        Node n = this.findForSure(k);
        return n.value;
    }

    // Insert given key and value into subtree rooted at given node; return
    // changed subtree with new node added. Unlike in find() above, doing this
    // recursively *has* benefits: First we get away with simpler code that
    // doesn't need parent pointers, second the recursive structure makes it
    // easier to add fancy rebalancing code.
    private Node insert(Node n, K k, V v) {
        if (n == null) {
            return new Node(k, v);
        }

        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            n.left = this.insert(n.left, k, v);
            // step B.2 part 3 from appendix
            //n.height = max(height(n.left), height(n.right)) + 1;
        } else if (cmp > 0) {
            n.right = this.insert(n.right, k, v);
            // step B.2 part 3 from appendix
            //n.height = max(height(n.left), height(n.right)) + 1;
        } else {
            throw new IllegalArgumentException("duplicate key " + k);
        }

        // step B.2 part 3 from appendix
        n.height = Math.max(this.height(n.left), this.height(n.right)) + 1;
        return this.balance(n);
    }

    @Override
    public void insert(K k, V v) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        this.root = this.insert(this.root, k, v);
        this.size++;
    }

    // Return node with maximum key in subtree rooted at given node.
    private Node max(Node n) {
        if (n.right == null) {
            return n;
        }
        return this.max(n.right);
    }

    // Remove given node and return the remaining tree. Easy if the node has 0
    // or 1 child; if it has two children, find the predecessor, copy its data
    // to the given node (thus removing the key we need to get rid off), then
    // remove the predecessor node.
    private Node remove(Node n) {
        // 0 and 1 child
        if (n.left == null) {
            return n.right;
        }
        if (n.right == null) {
            return n.left;
        }

        // 2 children
        Node max = this.max(n.left);
        n.key = max.key;
        n.value = max.value;
        n.left = this.remove(n.left, max.key);
        return n;
    }

    // Remove node with given key from subtree rooted at given node; return
    // changed subtree with given key missing. (Again doing this recursively
    // makes it easier to add fancy rebalancing code)
    private Node remove(Node n, K k) {
        if (n == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }

        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            n.left = this.remove(n.left, k);
        } else if (cmp > 0) {
            n.right = this.remove(n.right, k);
        } else {
            // Found node where key has to disappear, call helper method to
            // handle all the cases.
            n = this.remove(n);
        }
        // step B.2 part 3 from appendix
        //n.height = Math.max(height(n.left), height(n.right)) + 1;
        return this.balance(n);
    }

    @Override
    public V remove(K k) {
        // Need this additional find() for the V return value, because the
        // private remove() method cannot return that in addition to the new
        // root. If we had been smarter and used a void return type, we would
        // not need to do this extra work.
        V v = this.findForSure(k).value;
        this.root = this.remove(this.root, k);
        this.size--;
        return v;
    }

    // bring the right child up
    private Node rotateLeft(Node a) {
        Node b = a.right;
        a.right = b.left;
        b.left = a;
        a.height = Math.max(this.height(a.left), this.height(a.right)) + 1;
        b.height = Math.max(this.height(b.left), this.height(b.right)) + 1;
        return b;
    }

    // bring the left child up
    private Node rotateRight(Node a) {
        Node b = a.left;
        a.left = b.right;
        b.right = a;
        a.height = Math.max(this.height(a.left), this.height(a.right)) + 1;
        b.height = Math.max(this.height(b.left), this.height(b.right)) + 1;
        return b;
    }


    private Node rotateRightLeft(Node a) {
        a.right = this.rotateRight(a.right);
        return this.rotateLeft(a);
    }

    private Node rotateLeftRight(Node a) {
        a.left = this.rotateLeft(a.left);
        return this.rotateRight(a);
    }


    // general balancing method
    private Node balance(Node n) {

        if (n == null) {
            return n;
        }

        // if (Math.abs(n.left.height - n.right.height) <= 1) {
        if (Math.abs(this.height(n.left) - this.height(n.right)) <= 1) {
            n.height = Math.max(this.height(n.left), this.height(n.right)) + 1;
            return n;
        }
        //if (n.left.height > n.right.height) {
        if (this.height(n.left) > this.height(n.right)) {
            // case 1
            if (this.height(n.left.left) > this.height(n.left.right)) {
                return this.rotateRight(n);
            } else {
                // case 2
                return this.rotateLeftRight(n);
            }
        } else {
            if (this.height(n.right.left) > this.height(n.right.right)) {
                // case 4
                return this.rotateRightLeft(n);
            } else {
                // case 5
                return this.rotateLeft(n);
            }
        }
    }



    // Recursively add keys from subtree rooted at given node into the given
    // list in order.
    private void iteratorHelper(Node n, List<K> keys) {
        if (n == null) {
            return;
        }
        this.iteratorHelper(n.left, keys);
        keys.add(n.key);
        this.iteratorHelper(n.right, keys);
    }

    @Override
    public Iterator<K> iterator() {
        List<K> keys = new ArrayList<K>();
        this.iteratorHelper(this.root, keys);
        return keys.iterator();
    }

    // If we don't have a StringBuilder yet, make one; otherwise just reset it
    // back to a clean slate.
    private void setupStringBuilder() {
        if (this.stringBuilder == null) {
            this.stringBuilder = new StringBuilder();
        } else {
            this.stringBuilder.setLength(0);
        }
    }

    // Recursively append string representations of keys and values from
    // subtree rooted at given node in order.
    private void toStringHelper(Node n, StringBuilder s) {
        if (n == null) {
            return;
        }
        this.toStringHelper(n.left, s);
        s.append(n.key);
        s.append(": ");
        s.append(n.value);

        //comment this out!!
        //     s.append(" h= ");
        //     s.append(n.height);

        s.append(", ");
        this.toStringHelper(n.right, s);
    }

    @Override
    public String toString() {
        this.setupStringBuilder();
        this.stringBuilder.append("{");

        this.toStringHelper(this.root, this.stringBuilder);

        int length = this.stringBuilder.length();
        if (length > 1) {
            // If anything was appended at all, get rid of the last ", "
            // toStringHelper put in; easier to correct this after the fact
            // than to avoid making the mistake in the first place.
            this.stringBuilder.setLength(length - 2);
        }
        this.stringBuilder.append("}");

        return this.stringBuilder.toString();
    }
}
