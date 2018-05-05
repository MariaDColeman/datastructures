/*
 * Maria Coleman
 * mcolem31
 * using some of the code from Listing 17.1 HashMap Implementation
 * on the lecture notes
 */

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Map implemented with open addressing quadratic probing.
 * @param <K> key.
 * @param <V> value.
 */
public class QuadProbingHashMap<K, V> implements Map<K, V> {

    private static class Entry<K, V> {

        K key;
        V value;
        boolean deleted;

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
        private int returned;

        private Iterator<Entry<K, V>> slots;

        /** Constructor for this iterator. */
        HashMapIterator() {
            this.slots = QuadProbingHashMap.this.data.iterator();
        }

        public boolean hasNext() {
            return this.returned < QuadProbingHashMap.this.size;
        }

        public K next() {
            // System.out.println(this.slots.next().key);
            if (this.slots.hasNext()) {
                this.returned += 1;
                K temp = this.slots.next().key;
                while (temp == null) {
                    temp = this.slots.next().key;
                }
                return temp;
            } else {
                return null;
            }

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private int primeNum = 15;

    private final int INITIAL_SIZE = Primes.INTS[15];
    private double loadFactorMax = 0.5;

    private ArrayList<Entry<K, V>> data;
    private Entry<K, V> fake;
    private int size;
    private int numSlots;

    /** Constructor for this class. */
    public QuadProbingHashMap() {
        this.data = new ArrayList<Entry<K, V>>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            this.data.add(new Entry<K, V>(null, null));
        }

        this.numSlots = INITIAL_SIZE;

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
        int index = slot;
        int r = 1;

        //while (this.data.get(index).key != null) {
        while ((this.data.get(index).key != null)
            || (this.data.get(index).deleted)) {

            if ((this.data.get(index).key != null)
                && this.data.get(index).key.equals(k)) {
                return this.data.get(index);
            }
            index = (slot + r * r) % this.numSlots;
            r++;
        }
        return null;

    }

    /**
     * Force given integer positive. This is *not* abs, it just switches the
     * MSB off.
     * this is from UniversalHashes provided
     */
    private int pos(int i) {
        return i & ~(1 << 31);
    }

    private int hash2(Object o) {
        //        return this.pos(o.hashCode()) % this.data.size();
        return this.pos(o.hashCode()) % this.numSlots;

    }

    private int hash(Object o) {
        return this.abs(o.hashCode()) % this.numSlots;
    }


    private int abs(int i) {

        if (i < 0) {
            return -i;
        } else {
            return i;
        }

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
        e.deleted = true;
        int slot = this.hash(k);

        int index = slot;
        int r = 1;
        while (this.data.get(index).key != null) {

            index = (slot + r * r) % this.numSlots;
            r++;
        }

        // index is the first open slot that we want to insert at
        this.data.set(index, e);


        //this.data.get(slot).add(e);
        this.size += 1;


        // if the current load factor is greater than loadFactorMax
        // then allocate a bigger "outer" array and rehash old
        if (this.size * 1.0 / this.numSlots >= this.loadFactorMax) {
            ArrayList<Entry<K, V>> temp = this.data;
            if (this.primeNum >= 31) {
                this.numSlots = Primes.INTS[30];
            } else {
                this.numSlots = Primes.INTS[++this.primeNum];
            }
            this.size = 0;

            this.data = new ArrayList<Entry<K, V>>();
            for (int i = 0; i < this.numSlots; i++) {
                this.data.add(new Entry<K, V>(null, null));
            }


            //System.out.println("changing array size");

            for (Entry<K, V> e2 : temp) {
                if (e2.key != null) {
                    this.insert(e2.key, e2.value);
                }
            }

        }

    }

    @Override
    public V remove(K k) throws IllegalArgumentException {
        Entry<K, V> e = this.findForSure(k);
        int slot = this.hash(k);
        int index = slot;
        int r = 1;
        //        while (this.data.get(index) != null) {
        while ((this.data.get(index).key != null)
            || (this.data.get(index).deleted)) {

            if (this.data.get(index).key.equals(k)) {
                V tempVal = e.value;

                this.data.get(index).key = null;
                this.data.get(index).value = null;
                this.data.get(index).deleted = true;

                //      Entry<K, V> newE = new Entry<K, V>(null, null);
                //     this.data.set(index, newE);
                //this.data.set(index, null);

                this.size -= 1;
                //return e.value;
                return tempVal;
            }
            index = (slot + r * r) % this.numSlots;
            r++;
        }

        //never gets here
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }


}
