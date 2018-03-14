/*
 * Maria Coleman
 * mcolem31
 */

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
            if (this.forward) {
                return (this.current != null)
                    && (this.current != SentinelList.this.tail);
            } else {
                return (this.current != null)
                    && (this.current != SentinelList.this.head);
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
    // other SentinelList objects.
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

    /**
     * Method to handle throwing exception if empty to minimize repeated code.
     * @throws EmptyException if list is empty.
     */
    private void checkEmpty() throws EmptyException {
        if (this.empty()) {
            throw new EmptyException();
        }
    }

    /**
     * Method to remove a provided position in general to reduce repeated code.
     * @param n node to remove.
     */
    private void removeGeneral(Node<T> n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
        this.length -= 1;
        n.owner = null; // invalidate position
    }


    /**
     * Method to insert in general to reduce repeated code.
     * This will use a method of inserting after the provided position.
     * @param current node to insert after
     * @param t data to insert.
     * @return Position object of what was inserted
     */
    private Position<T> insertGeneral(Node<T> current, T t) {
        Node<T> n = new Node<T>();
        n.data = t;
        n.owner = this;
        n.prev = current;
        n.next = current.next;
        current.next.prev = n;
        current.next = n;
        this.length += 1;
        return n;
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
        this.checkEmpty();
        return this.head.next;
    }

    @Override
    public Position<T> back() throws EmptyException {
        this.checkEmpty();
        return this.tail.prev;
    }

    @Override
    public Position<T> insertFront(T t) {
        Node<T> current = this.head;
        return this.insertGeneral(current, t);
        //Node<T> n = new Node<T>();
        //n.data = t;
        //n.owner = this;
        //n.next = this.head.next;
        //this.head.next = n;
        //n.prev = this.head;
        //n.next.prev = n;
        //this.length += 1;
        //return n;
    }

    @Override
    public Position<T> insertBack(T t) {
        Node<T> current = this.tail.prev;
        return this.insertGeneral(current, t);
        //Node<T> n = new Node<T>();
        //n.data = t;
        //n.owner = this;
        //n.prev = this.tail.prev;
        //this.tail.prev.next = n;
        //this.tail.prev = n;
        //n.next = this.tail;
        //this.length += 1;
        //return n;
    }

    @Override
    public void removeFront() throws EmptyException {
        this.checkEmpty();
        Node<T> n = this.head.next;
        this.removeGeneral(n);
        // this.head.next = n.next;
        // this.head.next.prev = this.head;
        // this.length -= 1;
        // n.owner = null; // invalidate position
    }

    @Override
    public void removeBack() throws EmptyException {
        this.checkEmpty();
        Node<T> n = this.tail.prev;
        this.removeGeneral(n);
        //n.prev.next = this.tail;
        //this.tail.prev = n.prev;
        //this.length -= 1;
        //n.owner = null; // invalidate position
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
        return this.insertGeneral(current.prev, t);
        //Node<T> n = new Node<T>();
        //n.data = t;
        //n.owner = this;
        //n.prev = current.prev;
        //n.next = current;
        //current.prev.next = n;
        //current.prev = n;
        //this.length += 1;
        //return n;
    }

    @Override
    public Position<T> insertAfter(Position<T> p, T t)
    throws PositionException {
        Node<T> current = this.convert(p);
        return this.insertGeneral(current, t);
        //Node<T> n = new Node<T>();
        //n.data = t;
        //n.owner = this;
        //n.prev = current;
        //n.next = current.next;
        //current.next.prev = n;
        //current.next = n;
        //this.length += 1;
        //return n;
    }

    @Override
    public void remove(Position<T> p) throws PositionException {
        Node<T> n = this.convert(p);
        this.removeGeneral(n);
        // n.prev.next = n.next;
        // n.next.prev = n.prev;
        // this.length -= 1;
        // n.owner = null; // invalidate position
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
