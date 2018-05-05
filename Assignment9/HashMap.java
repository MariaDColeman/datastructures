/*
 * Maria Coleman
 * mcolem31
 * using some of the code from Listing 17.1 HashMap Implementation
 * on the lecture notes
 */

import java.util.ArrayList;
import java.util.Iterator;

public class HashMap<K, V> implements Map<K, V> {

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
            return this.key.hasCode();
        }
    }

    private class HashMapIterator implements Iterator<K> {




    }

}
