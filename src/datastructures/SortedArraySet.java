package datastructures;

import datastructures.BinarySearch.IndexToInsertInOrder;

public class SortedArraySet<T extends Comparable<T>> {

    private final DoubleEdgedLinearArray<T> array;

    /**
     * O(1)
     */
    public SortedArraySet() {
        array = new DoubleEdgedLinearArray<>();
    }

    /**
     * O(1)
     */
    public SortedArraySet(int initialCapacity) {
        array = new DoubleEdgedLinearArray<>(initialCapacity);
    }

    /**
     * O(n*log(n))
     */
    public SortedArraySet(T[] values) {
        array = new DoubleEdgedLinearArray<>(values);
        MergeSort.sort(array);
    }

    /**
     * O(log(n))
     * Finds value position
     */
    public int find(T value) {
        return BinarySearch.search(array, value);
    }

    /**
     * O(log(n)) finds what position to insert and insert it
     */
    public void insert(T value) {
        IndexToInsertInOrder idx = BinarySearch.findIndexToStaySorted(array, value);
        if (idx.match()) { // if it's a match, override
            array.set(idx.index(), value);
        } else { // else insert
            array.insertAt(idx.index(), value);
        }
    }

    /**
     * O(log(n)) deletes entry if it exists
     */
    public void delete(T value) {
        int idx = BinarySearch.search(array, value);
        if (idx == -1) {
            return;
        }

        array.deleteAt(idx);
    }

    /**
     * O(1)
     */
    public T first() {
        return array.first();
    }

    /**
     * O(1)
     */
    public T last() {
        return array.last();
    }

    /**
     * O(log(n))
     */
    public T findPrevious(T value) {
        int current = BinarySearch.search(array, value);
        if (current < 1) {
            return null;
        }

        return array.at(current - 1);
    }

    /**
     * O(log(n))
     */
    public T findNext(T value) {
        int current = BinarySearch.search(array, value);
        if (current < 0 || current >= size() - 1) {
            return null;
        }

        return array.at(current + 1);
    }

    /**
     * O(1)
     */
    public int size() {
        return array.size();
    }

    /**
     * O(n)
     */
    @Override
    public String toString() {
        return array.toString();
    }


}
