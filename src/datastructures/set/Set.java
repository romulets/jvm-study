package datastructures.set;

public interface Set<T> {

    boolean contains(T value);

    /**
     * I know this seems dumb, but it's super useful for HashMap.
     * We search by hash, but some metadata not present in the hash might be the result (KeyValue)
     *
     */
    T find(T value);

    void add(T value);

    T delete(T value);

    T first();

    T last();

    T findPrevious(T value);

    T findNext(T value);

    int size();
}
