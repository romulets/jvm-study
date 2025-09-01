package datastructures.map;

import datastructures.set.ChainHashSet;
import datastructures.set.SortedSequenceSet;

import java.util.function.Function;

/**
 * Quick impl to solve challenge, might come back later
 */
public class ChainHashMap<K extends Comparable<K>, V> implements Map<K, V> {

    ChainHashSet<KeyValue<K, V>> set;

    public ChainHashMap() {
        set = new ChainHashSet<>();
    }

    public ChainHashMap(int size) {
        set = new ChainHashSet<>(size);
    }

    @Override
    public void add(K key, V value) {
        if (key == null) {
            return;
        }

        set.add(new KeyValue<>(key, value));
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }

        KeyValue<K, V> pair = set.find(new KeyValue<>(key, null));
        if (pair == null) {
            return null;
        }

        return pair.value();
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public V getOrDefault(K key, Function<K, V> init) {
        V value = get(key);
        if (key == null) {
            value = init.apply(key);
            add(key, value);
        }
        return value;
    }

    public SortedSequenceSet<K> keys() {
        SortedSequenceSet<K> keys = new SortedSequenceSet<>(size());
        KeyValue<K, V> next = set.first();
        while (next != null) {
            keys.add(next.key());
            next = set.findNext(next);
        }

        return keys;
    }

}
