package datastructures.sequence;

public interface Sequence<T> {

    void insertLast(T value);

    void insertFirst(T value);

    void insertAt(int pos, T value);

    T deleteAt(int pos);

    T deleteLast();

    T deleteFirst();

    T at(int i);

    T first();

    T last();

    Sequence<T> subset(int begin, int end);

    void set(int i, T value);

    int size();
}
