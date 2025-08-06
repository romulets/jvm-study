package datastructures.set;

import datastructures.BinarySearch;
import datastructures.BinarySearch.IndexToInsertInOrder;
import datastructures.MergeSort;
import datastructures.array.Array;
import datastructures.array.DoubleEdgedLinearArray;

public class SortedArraySet<T extends Comparable<T>> implements Set<T> {

    private final Array<T> array;

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
     * Checks if value exists within set
     */
    @Override
    public boolean contains(T value) {
        return BinarySearch.search(array, value) > -1;
    }

    /**
     * O(log(n)) finds what position to insert and insert it
     */
    @Override
    public void add(T value) {
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
    @Override
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
    @Override
    public T first() {
        return array.first();
    }

    /**
     * O(1)
     */
    @Override
    public T last() {
        return array.last();
    }

    /**
     * O(log(n))
     */
    @Override
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
    @Override
    public T findNext(T value) {
        int current = BinarySearch.search(array, value);
        if (current < 0 || current >= size() - 1) {
            return null;
        }

        return array.at(current + 1);
    }

    @Override
    public T find(T value) {
        int pos = BinarySearch.search(array, value);
        if(pos < 0) {
            return null;
        }

        return array.at(pos);
    }

    /**
     * O(1)
     */
    @Override
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
