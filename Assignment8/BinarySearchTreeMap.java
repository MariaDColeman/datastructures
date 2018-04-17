import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Ordered maps implemented as (basic) binary search trees.
 *
 * These BSTs are *not* balanced so all operations (except for size) are O(n)
 * in the worst case. Iterators operate on a copy of the keys, so changing the
 * tree will not change iterations in progress. (Iterating over the tree
 * directly would require a "threaded" representation, a much more complicated
 * beast.)
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class BinarySearchTreeMap<K extends Comparable<? super K>, V>
    implements OrderedMap<K, V> {

    // Each node holds a key (which is what we sort the BST by) as well as a
    // value. We don't need a parent pointer as long as we use recursive code
    // for insert/remove.
    private class Node {
        Node left;
        Node right;
        K key;
        V value;

        // Constructor to make node creation easier to read.
        Node(K k, V v) {
            // left and right default to null
            this.key = k;
            this.value = v;
        }

        // Just for debugging purposes.
        public String toString() {
            return "Node<key: " + this.key
                + "; value: " + this.value
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
    // easier to add fancy rebalancing code later.
    private Node insert(Node n, K k, V v) {
        if (n == null) {
            return new Node(k, v);
        }

        int cmp = k.compareTo(n.key);
        if (cmp < 0) {
            n.left = this.insert(n.left, k, v);
        } else if (cmp > 0) {
            n.right = this.insert(n.right, k, v);
        } else {
            throw new IllegalArgumentException("duplicate key " + k);
        }

        return n;
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
    // makes it easier to add fancy rebalancing code later.)
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

        return n;
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
