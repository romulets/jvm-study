package datastructures.map;

import datastructures.set.AVLTreeSet;
import datastructures.tree.AVLTree;

import java.util.function.Function;

public class AVLTreeMap<K extends Comparable<K>, V> implements Map<K, V> {

    private final AVLTreeSet<KeyValue<K,V>> set;

    public AVLTreeMap() {
        set = new AVLTreeSet<>();
    }

    public AVLTreeMap(Function<KeyValue<K,V>, AVLTree<KeyValue<K,V>>> treeAdapter) {
        set = new AVLTreeSet<KeyValue<K,V>>(treeAdapter);
    }

    /**
     * O(log n)
     */
    @Override
    public void add(K key, V value) {
        set.add(new KeyValue<>(key,value));
    }

    /**
     * O(log n)
     */
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

    /**
     * O(log n)
     */
    @Override
    public V getOrDefault(K key, Function<K, V> init) {
        V value = get(key);
        if (value == null) {
            value = init.apply(key);
            add(key, value);
        }
        return value;
    }

    public AVLTreeSet<KeyValue<K,V>> set() {
        return set;
    }
}
