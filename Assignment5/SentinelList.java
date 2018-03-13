import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic position-based sentinel linked list.
 *
 * @param <T> Element type.
 */
public class SentinelList<T> implements List<T> {

    private static final class Node<T> implements Position<T> {
        // The usual doubly-linked list stuff.
        Node<T> next;
        Node<T> prev;
        T data;

        // List that created this node, to validate positions.
        List<T> owner;

        @Override
        public T get() {
            return this.data;
        }

        @Override
        public void put(T t) {
            this.data = t;
        }
    }

    private final class ListIterator implements Iterator<T> {
        Node<T> current;
        boolean forward;

        ListIterator(boolean f) {
            this.forward = f;
            if (this.forward) {
                this.current = SentinelList.this.head.next;
            } else {
                this.current = SentinelList.this.tail.prev;
            }
        }

        @Override
        public T next() throws NoSuchElementException {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            T t = this.current.get();
            if (this.forward) {
                this.current = this.current.next;
            } else {
                this.current = this.current.prev;
            }
            return t;
        }

        @Override
        public boolean hasNext() {
            //return (this.current != null);
            if (this.forward) {
                return (this.current != null) && (this.current.next != SentinelList.this.tail);
          
            } else {

                return (this.current != null) && (this.current.prev != SentinelList.this.head);
            }
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int length;

    /**
     * Create an empty list.
     */
    public SentinelList() {
        Node<T> n1 = new Node<T>();
        n1.data = null;
        n1.owner = this;
        Node<T> n2 = new Node<T>();
        n2.data = null;
        n2.owner = this;
        n1.next = n2;
        n2.prev = n1;
        this.head = n1;
        this.tail = n2;
    }

    // Convert a position back into a node. Guards against null positions,
    // positions from other data structures, and positions that belong to
    // other LinkedList objects. That about covers it?
    private Node<T> convert(Position<T> p) throws PositionException {
        try {
            Node<T> n = (Node<T>) p;
            if (n.owner != this) {
                throw new PositionException();
            }
            return n;
        } catch (NullPointerException | ClassCastException e) {
            throw new PositionException();
        }
    }

    @Override
    public boolean empty() {
        return this.length == 0;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public boolean first(Position<T> p) throws PositionException {
        Node<T> n = this.convert(p);
        return this.head.next == n;
    }

    @Override
    public boolean last(Position<T> p) throws PositionException {
        Node<T> n = this.convert(p);
        return this.tail.prev == n;
    }

    @Override
    public Position<T> front() throws EmptyException {
        if (this.empty()) {
            throw new EmptyException();
        }
        return this.head.next;
    }

    @Override
    public Position<T> back() throws EmptyException {
        if (this.empty()) {
            throw new EmptyException();
        }
        return this.tail.prev;
    }

    @Override
    public Position<T> insertFront(T t) {
        Node<T> n = new Node<T>();
        n.data = t;
        n.owner = this;
        n.next = this.head.next;
        //if (this.head.next != null) {
        //    this.head.next.prev = n;
        //}
        this.head.next = n;
        //if (this.tail.prev == null) {
        //    this.tail.prev = n;
        //}
        n.prev = this.head;
        n.next.prev = n;
        this.length += 1;
        return n;
    }

    @Override
    public Position<T> insertBack(T t) {
        Node<T> n = new Node<T>();
        n.data = t;
        n.owner = this;
        n.prev = this.tail.prev;
       // if (this.tail.prev != null) {
            this.tail.prev.next = n;
        //}
        this.tail.prev = n;
        //if (this.head.next == this.tail) {
        //    this.head.next = n;
        //}
        n.next = this.tail;
        this.length += 1;
        return n;
    }

    @Override
    public void removeFront() throws EmptyException {
        if (this.empty()) {
            throw new EmptyException();
        }
        Node<T> n = this.head.next;
        //assert (n.prev == null);
        //if (n.next != null) {
         //   n.next.prev = null;
        //}
        this.head.next = n.next;
        //if (this.tail.prev == n) {
            //this.tail.prev = null;
          //  this.tail.prev = this.head;
       // } else {
            this.head.next.prev = this.head;
       // }
        this.length -= 1;
        n.owner = null; // invalidate position
    }

    @Override
    public void removeBack() throws EmptyException {
        if (this.empty()) {
            throw new EmptyException();
        }
        Node<T> n = this.tail.prev;
        //assert (n.next == null);
        //if (n.prev != null) {
            n.prev.next = this.tail;
        //}
        this.tail.prev = n.prev;
        //if (this.head.next == n) {
          //  this.head.next = null;
        //}
        this.length -= 1;
        n.owner = null; // invalidate position
    }

    @Override
    public Position<T> next(Position<T> p) throws PositionException {
        if (this.last(p)) {
            throw new PositionException();
        }
        return this.convert(p).next;
    }

    @Override
    public Position<T> previous(Position<T> p) throws PositionException {
        if (this.first(p)) {
            throw new PositionException();
        }
        return this.convert(p).prev;
    }

    @Override
    public Position<T> insertBefore(Position<T> p, T t)
    throws PositionException {
        Node<T> current = this.convert(p);
        Node<T> n = new Node<T>();
        n.data = t;
        n.owner = this;
        n.prev = current.prev;
        n.next = current;
        //if (current.prev != null) {
            current.prev.next = n;
        //} else {
          //  this.head.next = n;
        //}
        current.prev = n;
        this.length += 1;
        return n;
    }

    @Override
    public Position<T> insertAfter(Position<T> p, T t)
    throws PositionException {
        Node<T> current = this.convert(p);
        Node<T> n = new Node<T>();
        n.data = t;
        n.owner = this;
        n.prev = current;
        n.next = current.next;
        //if (current.next != null) {
            current.next.prev = n;
        //} else {
          //  this.tail.prev = n;
        //}
        current.next = n;
        this.length += 1;
        return n;
    }

    @Override
    public void remove(Position<T> p) throws PositionException {
        Node<T> n = this.convert(p);
        //if (n.prev != null) {
            n.prev.next = n.next;
        //} else {
        //    this.head.next = n.next;
        //}
        //if (n.next != null) {
            n.next.prev = n.prev;
        //} else {
          //  this.tail.prev = n.prev;
        //}
        this.length -= 1;
        n.owner = null; // invalidate position
    }

    @Override
    public Iterator<T> forward() {
        return new ListIterator(true);
    }

    @Override
    public Iterator<T> backward() {
        return new ListIterator(false);
    }

    @Override
    public Iterator<T> iterator() {
        return this.forward();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        for (Node<T> n = this.head.next; n != this.tail; n = n.next) {
            s.append(n.data);
            if (n.next != this.tail) {
                s.append(", ");
            }
        }
        s.append("]");
        return s.toString();
    }
}
