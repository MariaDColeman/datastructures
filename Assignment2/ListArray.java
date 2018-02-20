import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array implementation using a linked list.
 *
 * The sole purpose of this (otherwise useless) implementation is
 * to show that we <b>can</b> implement a given interface in many
 * different ways. (Also it's a stepping stone for the SparseArray
 * homework.)
 *
 * @param <T> Element type.
 */
public class ListArray<T> implements Array<T> {
    // A nested Node class to build our linked list out of. We use a
    // nested class (instead of an inner class) here since we don't
    // need access to the ListArray object we're part of. Also, since
    // Node is already private to ListArray, there's no need to make
    // the fields of Node private. (That would be like trying to hide
    // them from ourselves.)
    private static final class Node<T> {
        T data;
        Node<T> next;

        Node(T t, Node<T> n) {
            this.data = t;
            this.next = n;
        }
    }

    // The not-so-obvious representation of our abstract Array: A
    // linked list with "length" nodes instead of a basic array of
    // "length" slots. We also keep an explicit length so we don't
    // have to re-compute it every time.
    private Node<T> first;
    private int length;

    /**
     * Create a new ListArray.
     *
     * @param n Length of array, must be &gt; 0.
     * @param t Initial value to store in each slot.
     * @throws LengthException if n &le; 0.
     */
    public ListArray(int n, T t) throws LengthException {
        if (n <= 0) {
            throw new LengthException();
        }

        // Initialize all positions as we promise in the specification.
        // Unlike in SimpleArray we cannot avoid the initialization even
        // if t == null since the nodes still have to be created. On the
        // upside we don't need a cast anywhere.
        for (int i = 0; i < n; i++) {
            this.prepend(t);
        }
    }

    // Insert a node at the beginning of the linked list and adjust
    // length accordingly.
    private void prepend(T t) {
        Node<T> n = new Node<>(t, this.first);
        this.first = n;
        this.length += 1;
    }

    // Find the node for a given index. We enforce the precondition
    // on index here so we don't have to duplicate the check in get()
    // and put() below.
    private Node<T> find(int index) throws IndexException {
        if (index < 0 || index >= this.length) {
            throw new IndexException();
        }

        Node<T> n = this.first;
        for (int i = 0; i < index; i++) {
            assert n != null; // We trust that we've created enough nodes.
            n = n.next;
        }
        return n;
    }

    @Override
    public T get(int i) throws IndexException {
        Node<T> n = this.find(i);
        return n.data;
    }

    @Override
    public void put(int i, T t) throws IndexException {
        Node<T> n = this.find(i);
        n.data = t;
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
        // Current position in the linked list.
        Node<T> current;

        ArrayIterator() {
            this.current = ListArray.this.first;
        }

        @Override
        public T next() throws NoSuchElementException {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            T t = this.current.data;
            this.current = this.current.next;
            return t;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Node<T> n = this.first; n != null; n = n.next) {
            sb.append(n.data);
            if (n.next != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
