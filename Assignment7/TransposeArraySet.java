/*
 * Maria Coleman
 * mcolem31
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Set implemented using plain Java arrays and transpose heuristic.
 *
 * Since the implementation of this is pretty boring, we decided to add an
 * example for a "fail-fast" iterator, just to spice things up. If you want
 * to understand these beasts in detail, you need to read up on (and think
 * through) the issues yourself.
 *
 * To make an iterator "fail-fast" we need to be able to tell that the data
 * structure has been modified since the iteration started. We use a version
 * number in the ArraySet class to achieve this. The number starts at 0 and
 * is incremented whenever a Set operation modifies the internal array. Each
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
public class TransposeArraySet<T> implements Set<T> {
    private int used; // contiguous rep, no gaps allowed
    private T[] data; // no duplicates in rep, see insert()
    private int version;

    /**
     * Create an empty set.
     */
    public TransposeArraySet() {
        this.data = (T[]) new Object[1];
    }

    private boolean full() {
        return this.used >= this.data.length;
    }

    private void grow() {
        T[] big = (T[]) new Object[2 * this.data.length];
        // faster than our own loop would be
        System.arraycopy(this.data, 0, big, 0, this.data.length);
        this.data = big;
    }

    private int find(T t) {
        for (int i = 0; i < this.used; i++) {
            if (this.data[i].equals(t)) {
                // need to move the element in question one slot towards
                // the front (index 0) by swapping
                // but need to check if its the first one!!
                if (i != 0) {
                    T temp = this.data[i];
                    this.data[i] = this.data[i - 1];
                    this.data[i - 1] = temp;

                    // need to increase version if ordering actually changed
                    this.version++;
                    return i - 1;
                }
                return i;

            }
        }
        return -1;
    }

    @Override
    public void insert(T t) {
        if (this.has(t)) {
            return;
        }
        if (this.full()) {
            this.grow();
        }
        this.data[this.used] = t;
        this.used++;
        this.version++;
    }

    @Override
    public void remove(T t) {
        int p = this.find(t);
        if (p == -1) {
            return;
        }
        if (p != this.used - 1) {
            // use last element to fill gap instead of moving everything
            this.data[p] = this.data[this.used - 1];
        }
        this.used--;
        this.version++;
        this.data[this.used] = null; // "optimization"
    }

    @Override
    public boolean has(T t) {
        return this.find(t) != -1;
    }

    @Override
    public int size() {
        return this.used;
    }

    @Override
    public Iterator<T> iterator() {
        return new SetIterator();
    }

    private class SetIterator implements Iterator<T> {
        int current;
        int version;

        SetIterator() {
            this.version = TransposeArraySet.this.version;
        }

        private void checkVersion() {
            if (this.version != TransposeArraySet.this.version) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            this.checkVersion();
            return this.current < TransposeArraySet.this.used;
        }

        @Override
        public T next() throws NoSuchElementException {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            T t = TransposeArraySet.this.data[this.current];
            this.current++;
            return t;
        }
    }
}
