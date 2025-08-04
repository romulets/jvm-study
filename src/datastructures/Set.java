package datastructures;

public interface Set<T extends Comparable<T>> {

    int find(T value);

    void insert(T value);

    void delete(T value);

    T first();

    T last();

    T findPrevious(T value);

    T findNext(T value);

    int size();
}
