package datastructures.set;

public interface Set<T> {

    boolean contains(T value);

    void add(T value);

    void delete(T value);

    T first();

    T last();

    T findPrevious(T value);

    T findNext(T value);

    int size();
}
