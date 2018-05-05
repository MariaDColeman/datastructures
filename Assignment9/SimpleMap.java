import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Maps from arbitrary keys to arbitrary values.
 *
 * This implementation uses an unsorted ArrayList internally so all operations
 * take O(n) time. The iterator() method makes a copy of all keys, so it takes
 * O(n) as well. The only saving grace is simplicity.
 *
 * We could do slightly better (in several ways!) by using two parallel arrays
 * and managing our own insert/remove. Think about it.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class SimpleMap<K, V> implements Map<K, V> {
    // Entry pairs up a key and a value.
    private final class Entry {
        final K key;
        V value;

        Entry(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }

    private List<Entry> data;

    /**
     * Create an empty map.
     */
    public SimpleMap() {
        this.data = new ArrayList<>();
    }

    // Find entry for key k, throw exception if k is null.
    private Entry find(K k) {
        if (k == null) {
            throw new IllegalArgumentException("cannot handle null key");
        }
        for (Entry e: this.data) {
            if (k.equals(e.key)) {
                return e;
            }
        }
        return null;
    }

    // Find entry for key k, throw exception if k not mapped.
    private Entry findForSure(K k) {
        Entry e = this.find(k);
        if (e == null) {
            throw new IllegalArgumentException("cannot find key " + k);
        }
        return e;
    }

    @Override
    public void insert(K k, V v) {
        Entry e = this.find(k);
        if (e != null) {
            throw new IllegalArgumentException("duplicate key " + k);
        }
        e = new Entry(k, v);
        this.data.add(e);
    }

    @Override
    public V remove(K k) {
        Entry e = this.findForSure(k);
        V v = e.value;
        this.data.remove(e);
        return v;
    }

    @Override
    public void put(K k, V v) {
        Entry e = this.findForSure(k);
        e.value = v;
    }

    @Override
    public V get(K k) {
        Entry e = this.findForSure(k);
        return e.value;
    }

    @Override
    public boolean has(K k) {
        if (k == null) {
            return false;
        }
        return this.find(k) != null;
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public Iterator<K> iterator() {
        List<K> keys = new ArrayList<>();
        for (Entry e: this.data) {
            keys.add(e.key);
        }
        return keys.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Entry e: this.data) {
            sb.append(e.key).append(": ").append(e.value).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2); // Remove the last ", " we put in.
        }
        sb.append("}");
        return sb.toString();
    }
}
