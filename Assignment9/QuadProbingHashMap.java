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
        //private Iterator <ArrayList<Entry<K, V>>> outer;
        //private Iterator <Entry<K, V>> inner;

        private Iterator <Entry<K, V>> slots;

        HashMapIterator() {
           // this.outer = QuadProbingHashMap.this.data.iterator();
         //   this.inner = this.outer.next().iterator();
//System.out.println("starting");
            this.slots = QuadProbingHashMap.this.data.iterator();
        }
        public boolean hasNext() {
            
            return this.returned < QuadProbingHashMap.this.size;
        }
        public K next() {
          // System.out.println(this.slots.next().key);
            if (this.slots.hasNext()) {
                this.returned += 1;
// System.out.println(this.slots.next().key);
 //               return this.slots.next().key;
               K temp = this.slots.next().key;
               while (temp == null) {
                   temp = this.slots.next().key;
               }

                //K temp = this.slots.next().key;
  // System.out.println(temp);            
//                this.returned += 1;
                return temp;
            } else {
//System.out.println("no next");
                return null;
            }



       //     if (this.inner.hasNext()) {
       //         this.returned += 1;
        //        return this.inner.next().key;
       //     } else {
       //         while (!this.inner.hasNext() && this.outer.hasNext()) {
       //             this.inner = this.outer.next().iterator();
       //         }
       //         if (this.inner.hasNext()) {
       //             this.returned += 1;
       //             return this.inner.next().key;
       //         } else {
       //             return null;
       //         }
       //     }

        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private int primeNum = 10;
    public static final int INITIAL_SIZE = Primes.INTS[10];
//    public static final double loadFactorMax = 0.9;
    private double loadFactorMax = 0.5;

    private ArrayList<Entry<K, V>> data;
    private Entry<K, V> fake;
    private int size;
    private int numSlots;

    public QuadProbingHashMap() {
//        this.data = new ArrayList<>();
//        for (int i = 0; i < INITIAL_SIZE; i++) {
//            this.data.add(new ArrayList<Entry<K, V>>());
//            this.numSlots++;
//        }
      //  System.out.println(INITIAL_SIZE);
//        this.data = new ArrayList<Entry<K, V>>(INITIAL_SIZE);
        this.data = new ArrayList<Entry<K, V>>();
        for (int i = 0; i < INITIAL_SIZE; i++) {
            this.data.add(new Entry<K, V>(null,null));
        //    this.numSlots++;
        }

        this.numSlots = INITIAL_SIZE;
        //this.numSlots = INITIAL_SIZE;
//System.out.println(this.data.size());
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
//        while (this.data.get(index) != null) {
        while (this.data.get(index).key != null) {

            if (this.data.get(index).key.equals(k)) {
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

    private int hash(Object o) {
//        return this.pos(o.hashCode()) % this.data.size();
        return this.pos(o.hashCode()) % this.numSlots;

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

        int index = slot;
        int r = 1;
//        while (this.data.get(index) != null) {
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
        if (this.size*1.0/this.numSlots >= this.loadFactorMax) {
            ArrayList<Entry<K, V>> temp = this.data;
            if (primeNum >= 31) {
                this.numSlots = Primes.INTS[30];
            } else {
            this.numSlots = Primes.INTS[++primeNum];
            }
//            this.data = new ArrayList<>();
            //this.numSlots = 2 * this.numSlots;
//            this.numSlots = Prime.INTS(++primeNum);
            this.size = 0;
           // for (int i = 0; i < numSlots; i++) {
           //     this.data.add(new ArrayList<Entry<K, V>>());
           // }

        this.data = new ArrayList<Entry<K, V>>();
        for (int i = 0; i < this.numSlots; i++) {
            this.data.add(new Entry<K, V>(null,null));
        }




            for (Entry<K, V> e2 : temp) {
                //for (Entry<K, V> e2 : ea) {
                    if (e2.key != null) {
                        this.insert(e2.key, e2.value);
                    }
                //}
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
        while (this.data.get(index).key != null) {

            if (this.data.get(index).key.equals(k)) {
         
                Entry<K, V> newE = new Entry<K, V>(null, null);
                this.data.set(index, newE);
                //this.data.set(index, null);
             

         

                this.size -= 1;
                return e.value;
            }
            index = (slot + r * r) % this.numSlots;
            r++;
        }

        //never gets here
        return null;
        //this.data.get(slot).remove(e);
        //this.size -= 1;
        //return e.value;
    }

    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    
 //   public Iterator<K> iterator() {
 //       return (new ArrayList<K>(this.data)).iterator();
 //   }



}
