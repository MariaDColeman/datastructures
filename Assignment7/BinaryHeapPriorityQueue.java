/*
 * Maria Coleman
 * mcolem31
 */

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Priority queue implemented as a binary heap.
 *
 * @param <T> Element type.
 */
public class BinaryHeapPriorityQueue<T extends Comparable<? super T>>
    implements PriorityQueue<T> {

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
     * A binary heap array using the "natural" ordering of T.
     */
    public BinaryHeapPriorityQueue() {
        this(new DefaultComparator<>());
    }

    /**
     * A binary heap array using the given comparator for T.
     * @param cmp Comparator to use.
     */
    public BinaryHeapPriorityQueue(Comparator<T> cmp) {
        this.data = new ArrayList<>();
        this.data.add(null);
        this.cmp = cmp;
    }

    // Value in slot i "less" than value in slot j? Note that the
    // comparator determines what we consider "less" here.
    private boolean less(int i, int j) {
        return this.cmp.compare(this.data.get(i), this.data.get(j)) < 0;
    }

    // Value in slot i "greater" than value in slot j? Note that the
    // comparator determines what we consider "greater" here.
    private boolean greater(int i, int j) {
        return this.cmp.compare(this.data.get(i), this.data.get(j)) > 0;
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

    private int parent(int i) {
        int p = i / 2;
        if (p != 0) {
            return p;
        } else {
            return 1; // it is the root
        }
    }

    private int leftChild(int i) {
        int l = 2 * i;
        if (l < this.data.size()) {
            return l;
        } else {
            return i; // node at i doesnt have left child
        }
    }

    private int rightChild(int i) {
        int r = 2 * i + 1;
        if (r < this.data.size()) {
            return r;
        } else {
            return i; // node at i doesnt have right child
        }
    }


    // swap values in i and j positions in this.data
    private void swap(int i, int j) {
        T temp = this.data.get(i);
        this.data.set(i, this.data.get(j));
        this.data.set(j, temp);
    }

    @Override
    public void insert(T t) {
        this.data.add(t);
        int j = this.data.size() - 1;
        int p = this.parent(j);
        while (this.greater(p, j)) {
            this.swap(p, j);
            j = p;
            p = this.parent(j);
        }
    }

    @Override
    public T remove() throws EmptyException {
        if (this.empty()) {
            throw new EmptyException();
        }
        T toRemove = this.data.get(1);
        T t = this.data.remove(this.data.size() - 1);
        if (this.empty()) {
            return t;
        }
        this.data.set(1, t);

        int j = 1;
        int l = this.leftChild(j);
        int r = this.rightChild(j);
        int best;
        if (this.less(l, r)) {
            best = l;
        } else {
            best = r;
        }

        while (this.greater(j, best)) {
            this.swap(j, best);
            j = best;
            l = this.leftChild(j);
            r = this.rightChild(j);

            if (this.less(l, r)) {
                best = l;
            } else {
                best = r;
            }
        }

        return toRemove;
    }

    @Override
    public T best() throws EmptyException {
        if (this.empty()) {
            throw new EmptyException();
        }
        return this.data.get(1);
    }

    @Override
    public boolean empty() {
        return this.data.size() == 1;
    }
}
