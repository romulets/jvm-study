package datastructures.map;

import java.util.function.Function;

public interface Map<K extends Comparable<K>, V> {
    void add(K key, V value);

    V get(K key);

    int size();

    V getOrDefault(K key, Function<K, V> init);
}
