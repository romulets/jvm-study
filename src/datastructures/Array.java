package datastructures;

public interface Array<T> {

    void insertLast(T value);

    void insertFirst(T value);

    void insertAt(int pos, T value);

    void deleteAt(int pos);

    T deleteLast();

    T deleteFirst();

    T at(int i);

    T first();

    T last();

    Array<T> subset(int begin, int end);

    void set(int i, T value);

    int size();
}
