import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array implementation using a linked list for SparseArray.
 *
 * Maria Coleman
 * mcolem31
 *
 * The sole purpose of this (otherwise useless) implementation is
 * to show that we <b>can</b> implement a given interface in many
 * different ways.
 * A sparse array is an array where relatively few positions have
 * values that differ from the initial value set upon creation.
 * Values are stored only for the positions that have actually been
 * changed.
 * Therefore, you should choose SparseArray if relaticely few values
 * differ from the initial value, and SimpleArray if many values do
 * change.
 *
 * @param <T> Element type.
 */
public class SparseArray<T> implements Array<T> {
    // A nested Node class to build our linked list out of. We use a
    // nested class (instead of an inner class) here since we don't
    // need access to the SparseArray object we're part of. Also, since
    // Node is already private to SparseArray, there's no need to make
    // the fields of Node private. (That would be like trying to hide
    // them from ourselves.)
    private static final class Node<T> {
        T data;
        Node<T> next;
        int position;

        Node(T t, Node<T> n, int p) {
            this.data = t;
            this.next = n;
            this.position = p;
        }
    }

    // We keep an explicit length so we don't
    // have to re-compute it every time.
    // We also keep track of how many nodes there are
    private Node<T> first;
    private int length;
    private T initial;
    private int listLength;

    /**
     * Create a new SparseArray.
     *
     * @param n Length of array, must be &gt; 0.
     * @param t Initial value
     * @throws LengthException if n &le; 0.
     */
    public SparseArray(int n, T t) throws LengthException {
        if (n <= 0) {
            throw new LengthException();
        }

        this.initial = t;
        this.length = n;
        this.listLength = 0;
        // There is initially an empty list because no values are
        // different than the initial value
    }

    // Insert a node at the beginning of the linked list and adjust
    // listLength accordingly.
    private void prepend(T t, int p) {
        Node<T> n = new Node<>(t, this.first, p);
        this.first = n;
        this.listLength += 1;
    }

    private void remove(int index) {
        // dont have to throw any exceptions since at this point the
        // index has already been checked in modified and find

        Node<T> n = this.first;

        Node<T> prev = null;

        while (n.position != index) {
            prev = n;
            n = n.next;
        }

        // n is now the node we want to remove

        // head if n == this.first
        if (n == this.first) {
            this.first = n.next;
            this.listLength -= 1;
            return;
        }
        this.listLength -= 1;
        prev.next = n.next;

    }

    private int listLen() {
        int len = 0;
        for (Node<T> n = this.first; (n != null); n = n.next) {
            len++;
        }
        //System.out.println(len);
        return len;
    }

    private boolean modified(int index) throws IndexException {
        if (index < 0 || index >= this.length) {
            throw new IndexException();
        }

        boolean modified = false;
        Node<T> n = this.first;

        for (int i = 0; (n != null) && (i < this.length) && !modified; i++) {
            if (n.position == index) {
                modified = true;
            } else {
                n = n.next;
            }
        }
        return modified;
    }




    // Find the node for a given index. We enforce the precondition
    // on index here so we don't have to duplicate the check in get()
    // and put() below.
    private Node<T> find(int index) throws IndexException {
        if (index < 0 || index >= this.length) {
            throw new IndexException();
        }

        Node<T> n = this.first;
        while (n.position != index) {
            n = n.next;
        }
        return n;
    }

    @Override
    public T get(int i) throws IndexException {
        if (this.modified(i)) {
            Node<T> n = this.find(i);
            return n.data;
        } else {
            return this.initial;
        }
    }

    @Override
    public void put(int i, T t) throws IndexException {
        if (this.modified(i)) {
            Node<T> n = this.find(i);
            n.data = t;
            if (n.data == this.initial) {
                //System.out.println(this.toString());
                int initialListLength = this.listLength;
                //System.out.println("len says");
                //System.out.println(this.listLen());
                //System.out.println("var says");
                //System.out.println(initialListLength);
                this.remove(i);
                // assert the value we store for listLength is correct
                assert this.listLength == initialListLength - 1;
                // assert the value we say is listLength is the correct
                // length of list of nodes
                //System.out.println("var says");
                //System.out.println(this.listLength);
                //System.out.println("fn says");
                //System.out.println(this.listLen());
                assert this.listLength == this.listLen();
            }
        } else {
            this.prepend(t, i);
        }
    }


    @Override
    public int length() {
        return this.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    // An iterator to traverse the array from front to back. Note
    // that we use an inner class here (not a nested class) so we
    // can access the outer object's "this" reference. If we didn't
    // do that, we'd have to pass the outer object (or at least the
    // first node) to the iterator's constructor.
    private final class ArrayIterator implements Iterator<T> {
        // Current position in the Sparse Array
        int currentIndex;

        @Override
        public T next() throws NoSuchElementException {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            T t = SparseArray.this.initial;
            if (SparseArray.this.modified(this.currentIndex)) {
                t = SparseArray.this.find(this.currentIndex).data;
            }

            this.currentIndex += 1;
            return t;
        }

        @Override
        public boolean hasNext() {
            return this.currentIndex < SparseArray.this.length;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < this.length; i++) {
            if (this.modified(i)) {
                sb.append(this.find(i).data).append(", ");
            } else {
                sb.append(this.initial).append(", ");
            }
        }

        sb.setLength(sb.length() - 2); //remove the last ", " put in

        sb.append("]");
        return sb.toString();
    }
}
