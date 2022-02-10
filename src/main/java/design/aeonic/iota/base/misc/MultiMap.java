package design.aeonic.iota.base.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A generic multimap structure implementation.
 * @author aeonic-dev
 * @param <K> the key data type
 * @param <V> the value data type
 */
@SuppressWarnings("serial")
public class MultiMap<K, V> extends HashMap<K, List<V>> {

    /**
     * Adds a value to the multimap.
     * @param key the key
     * @param val the value
     */
    public void add(K key, V val) {
        var arr = super.get(key);

        if (arr == null) {
            put(key, new ArrayList<V>());
            arr = super.get(key);
        }

        arr.add(val);
    }

    /**
     * Adds a list of values to the map.
     * @param key the key
     * @param vals a list of the values
     */
    public void add(K key, List<V> vals) {
        for (V val: vals) {
            add(key, val);
        }
    }

    /**
     * Merges another multimap into this one.
     * @param other the multimap to add
     */
    public void merge(MultiMap<K, V> other) {
        for (K key: other.keySet()) {
            add(key, other.get(key));
        }
    }

}