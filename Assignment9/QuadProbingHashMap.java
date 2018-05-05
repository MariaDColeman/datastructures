/*
 * Maria Coleman
 * mcolem31
 * using some of the code from Listing 17.1 HashMap Implementation
 * on the lecture notes
 */

import java.util.ArrayList;
import java.util.Iterator;

public class QuadProbingHashMap<K, V> implements Map<K, V> {

    private static class Entry<K, V> {

        K key;
        V value;
        Entry(K k, V v) {
            this.key = k;
            this.value = v;
        }
        public boolean equals(Object that) {
            return (that instanceof Entry)
                && (this.key.equals(((Entry) that).key));
        }
        public int hashCode() {
            return this.key.hashCode();
        }
    }

    private class HashMapIterator implements Iterator<K> {
        private int returned = 0;
        private Iterator <ArrayList<Entry<K, V>>> outer;
        private Iterator <Entry<K, V>> inner;
        HashMapIterator() {
            this.outer = HashMap.this.data.iterator();
            this.inner = this.outer.next().iterator();
        }
        public boolean hasNext() {
            return this.returned < HashMap.this.size;
        }
        public K next() {
            if (this.inner.hasNext()) {
                this.returned += 1;
                return this.inner.next().key;
            } else {
                while (!this.inner.hasNext() && this.outer.hasNext()) {
                    this.inner = this.outer.next().iterator();
                }
                if (this.inner.hasNext()) {
                    this.returned += 1;
                    return this.inner.next().key;
                } else {
                    return null;
                }
            }

        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    public static final int INITIAL_SIZE = 4096;
//    public static final double loadFactorMax = 0.9;
    private double loadFactorMax = 0.75;

    private ArrayList<ArrayList<Entry<K, V>>> data;
    private Entry<K, V> fake;
    private int size;
    private int numSlots;

    public QuadProbingHashMap() {
        this.data = new ArrayList<>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            this.data.add(new ArrayList<Entry<K, V>>());
            this.numSlots++;
        }
        this.fake = new Entry<K, V>(null, null);
    }

    @Override
    public boolean has(K k) {
        if (k == null) {
            return false;
        }
        return this.find(k) != null;
    }

    private Entry<K, V> find(K k) {
        if (k == null) {
            throw new IllegalArgumentException();
        }
        int slot = this.hash(k);
        this.fake.key = k;
        int index = this.data.get(slot).indexOf(this.fake);
        if (index == -1) {
            return null;
        } else {
            return this.data.get(slot).get(index);
        }
    }

    /**
     * Force given integer positive. This is *not* abs, it just switches the
     * MSB off.
     * this is from UniversalHashes provided
     */
    private int pos(int i) {
        return i & ~(1 << 31);
    }

    private int hash(Object o) {
        return this.pos(o.hashCode()) % this.data.size();
    }

    @Override
    public void put(K k, V v) throws IllegalArgumentException {
        Entry<K, V> e = this.findForSure(k);
        e.value = v;
    }

    @Override
    public V get(K k) throws IllegalArgumentException {
        Entry<K, V> e = this.findForSure(k);
        return e.value;
    }

    private Entry<K, V> findForSure(K k) throws IllegalArgumentException {
        Entry<K, V> e = this.find(k);
        if (e == null) {
            throw new IllegalArgumentException();
        }
        return e;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void insert(K k, V v) throws IllegalArgumentException {
        if (this.has(k)) {
            throw new IllegalArgumentException();
        }
        Entry<K, V> e = new Entry<K, V>(k, v);
        int slot = this.hash(k);
        this.data.get(slot).add(e);
        this.size += 1;


        // if the current load factor is greater than loadFactorMax
        // then allocate a bigger "outer" array and rehash old
        if (this.size*1.0/this.numSlots >= this.loadFactorMax) {
            ArrayList<ArrayList<Entry<K, V>>> temp = this.data;
            this.data = new ArrayList<>();
            this.numSlots = 2 * this.numSlots;
            this.size = 0;
            for (int i = 0; i < numSlots; i++) {
                this.data.add(new ArrayList<Entry<K, V>>());
            }
            for (ArrayList<Entry<K, V>> ea : temp) {
                for (Entry<K, V> e2 : ea) {
                    if (e2 != null) {
                        this.insert(e2.key, e2.value);
                    }
                }
            }

        }

    }

    @Override
    public V remove(K k) throws IllegalArgumentException {
        Entry<K, V> e = this.findForSure(k);
        int slot = this.hash(k);
        this.data.get(slot).remove(e);
        this.size -= 1;
        return e.value;
    }

    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

}
