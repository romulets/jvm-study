package datastructures.map;

import java.util.Objects;

public record KeyValue<K extends Comparable<K>, V>(K key, V value) implements Comparable<KeyValue<K,V>> {
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

    @Override
    public int compareTo(KeyValue<K, V> o) {
        if (o == null) {
            return 1;
        }

        return key.compareTo(o.key);
    }
}
