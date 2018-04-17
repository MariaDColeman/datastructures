/*
 * Maria Coleman
 * mcolem31
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Set implemented using plain Java list with Move to Front heuristic.
 *
 * Since the implementation of this is pretty boring, we decided to add an
 * example for a "fail-fast" iterator, just to spice things up. If you want
 * to understand these beasts in detail, you need to read up on (and think
 * through) the issues yourself.
 *
 * To make an iterator "fail-fast" we need to be able to tell that the data
 * structure has been modified since the iteration started. We use a version
 * number in the ListSet class to achieve this. The number starts at 0 and
 * is incremented whenever a Set operation modifies the internal list. Each
 * iterator also "remembers" the version number it was created for. We can
 * then check for modifications by comparing version numbers in the Iterator
 * operations: If we notice a mismatch, we throw an exception.
 *
 * (The Java documentation for ConcurrentModificationException states that
 * we should *not* test for it being thrown. The reason for this is that the
 * exception was originally used in a non-deterministic context. Since we are
 * using it in a very much deterministic one we *could* test for it. But it's
 * a messy situation already, so don't worry about it in the tests you write.)
 *
 * @param <T> Element type.
 */
public class MoveToFrontListSet<T> implements Set<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;
    }

    private Node<T> head;
    private int length;
    private int version;

    private Node<T> find(T t) {
        Node<T> prev = this.head;
        for (Node<T> n = this.head; n != null; n = n.next) {
            if (n.data.equals(t)) {

                // need to move the element in question  to the
                // front (head) of the list
                // but need to check if its the head!

                if (n != this.head) {
                    prev.next = n.next;
                    n.next = this.head;
                    this.head = n;
                    this.version++;
                    return n;
                }
                return n;

            }
            prev = n;

        }
        return null;
    }

    @Override
    public void insert(T t) {
        if (this.has(t)) {
            return;
        }
        Node<T> n = new Node<T>();
        n.data = t;
        n.next = this.head;
        n.prev = null;
        if (this.head != null) {
            this.head.prev = n;
        }
        this.head = n;
        this.length++;
        this.version++;
    }

    @Override
    public void remove(T t) {
        Node<T> position = this.find(t);
        if (position == null) {
            return;
        }
        if (position.next != null) {
            position.next.prev = position.prev;
        }
        if (position.prev != null) {
            position.prev.next = position.next;
        } else {
            this.head = position.next;
        }
        this.length--;
        this.version++;
    }

    @Override
    public boolean has(T t) {
        return this.find(t) != null;
    }

    @Override
    public int size() {
        return this.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new SetIterator();
    }

    private class SetIterator implements Iterator<T> {
        private Node<T> current;
        private int version;

        SetIterator() {
            this.current = MoveToFrontListSet.this.head;
            this.version = MoveToFrontListSet.this.version;
        }

        private void checkVersion() throws ConcurrentModificationException {
            if (this.version != MoveToFrontListSet.this.version) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            this.checkVersion();
            return this.current != null;
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
    }
}
