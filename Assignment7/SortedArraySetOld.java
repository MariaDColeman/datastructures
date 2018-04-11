/*
 * Maria Coleman
 * mcolem31
 * I did use some of the code provided in Problem 4 here.
 */


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Ordered Set implemented using Javas ArrayList.
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
public class SortedArraySet<T extends Comparable<? super T>> implements OrderedSet<T> {
    private int used; // contiguous rep, no gaps allowed
    private T[] data; // no duplicates in rep, see insert()
    private int version;

    // The default comparator uses the "natural" ordering.
    private static class DefaultComparator<T extends Comparable<? super T>>
        implements Comparator<T> {
        public int compare(T t1, T t2) {
            return t1.compareTo(t2);
        }
    }

    private ArrayList<T> data;
    private Comparator<T> cmp;

    /**
     * A sorted array using the "natural" ordering of T.
     */
    public SortedArraySet() {
        this(new DefaultComparator<>());
    }

    /**
     * A sorted array using the given comparator for T.
     * @param cmp Comparator to use.
     */
    public SortedArraySet(Comparator<T> cmp) {
        this.data = new ArrayList<>();
//        this.data = (T[]) new Object[1];
        this.data.add(null);
        this.cmp = cmp;
    }


    // Value in slot i "less" than value in slot j? Note that the
    // comparator determines what we consider "less" here.
    private boolean less(int i, int j) {
        return this.cmp.compare(this.data.get(i), this.data.get(j)) < 0;
    }

    // Find position in data[] where t should live.
    private int find(T t) {
        this.data.set(0, t); // set sentinel
        int l = 1;
        int u = this.data.size() - 1;
        while (l <= u) {
            int m = (l + u) / 2;
            if (this.less(0, m)) {
                u = m - 1;
            } else if (this.less(m, 0)) {
                l = m + 1;
            } else {
                return m;
            }
        }
        return l;
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
            this.version = SortedArraySet.this.version;
        }

        private void checkVersion() {
            if (this.version != SortedArraySet.this.version) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            this.checkVersion();
            return this.current < SortedArraySet.this.used;
        }

        @Override
        public T next() throws NoSuchElementException {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            T t = SortedArraySet.this.data[this.current];
            this.current++;
            return t;
        }
    }
}
