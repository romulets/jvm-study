package datastructures.map;

import datastructures.set.ChainHashSet;
import datastructures.set.SortedSequenceSet;

import java.util.Objects;

/**
 * Quick impl to solve challenge, might come back later
 */
public class ChainHashMap<K extends Comparable<K>, V> {

    record KeyValue<K, V>(K key, V value) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            KeyValue<?, ?> keyValue = (KeyValue<?, ?>) o;
            return Objects.equals(key, keyValue.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

    }

    ChainHashSet<KeyValue<K, V>> set;

    public ChainHashMap() {
        set = new ChainHashSet<>();
    }

    public ChainHashMap(int size) {
        set = new ChainHashSet<>(size);
    }

    public void add(K key, V value) {
        if (key == null) {
            return;
        }

        set.add(new KeyValue<>(key, value));
    }

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

    public int size() {
        return set.size();
    }

    public SortedSequenceSet<K> keys() {
        SortedSequenceSet<K> keys = new SortedSequenceSet<>(size());
        KeyValue<K, V> next = set.first();
        while (next != null) {
            keys.add(next.key);
            next = set.findNext(next);
        }

        return keys;
    }

}
