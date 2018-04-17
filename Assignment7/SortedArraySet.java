/*
 * Maria Coleman
 * mcolem31
 * I did use some of the code provided in the online lecture notes
 * on ordered sets.
 */

//import java.util.ConcurrentModificationException;
import java.util.Iterator;
//import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Ordered Set implemented using javas ArrayList.
 *
 * @param <T> Element type.
 */
public class SortedArraySet<T extends Comparable<? super T>>
    implements OrderedSet<T> {
    private int used; // contiguous rep, no gaps allowed
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
        this.cmp = cmp;
    }


    // Value in slot i "less" than value in slot j? Note that the
    // comparator determines what we consider "less" here.
    private boolean less(int i, int j) {
        return this.cmp.compare(this.data.get(i), this.data.get(j)) < 0;
    }

    // Value in slot i "greater" than t? Note that the
    // comparator determines what we consider "greater" here.
    private boolean greater(int i, T t) {
        return this.cmp.compare(this.data.get(i), t) > 0;
    }

    // Value in slot i "equal" to t? Note that the
    // comparator determines what we consider "equal" here.
    private boolean equalsTo(int i, T t) {
        return this.cmp.compare(this.data.get(i), t) == 0;
    }

    private int find(T t) {
        int l = 0;
        int u = this.data.size() - 1;
        while (l <= u) {
            int m = (l + u) / 2;
            if (this.greater(m, t)) {
                u = m - 1;
            } else if (this.equalsTo(m, t)) {
                return m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    // the find method tells us the correct position for and elem.
    // need to know if we have found the exact elem looking for
    private boolean found(int p, T t) {
        return p < this.data.size() && this.data.get(p).equals(t);
    }

    @Override
    public void insert(T t) {
        int p = this.find(t);
        if (this.found(p, t)) {
            return;
        }
        this.data.add(p, t);
        this.used++;
        this.version++;
    }


    @Override
    public void remove(T t) {
        int p = this.find(t);
        if (!this.found(p, t)) {
            return;
        }
        this.data.remove(p);
        this.used--;
        this.version++;
    }

    @Override
    public boolean has(T t) {
        int p = this.find(t);
        return this.found(p, t);
    }

    @Override
    public int size() {
        return this.used;
    }

    @Override
    public Iterator<T> iterator() {
        return (new ArrayList<T>(this.data)).iterator();
    }

}
