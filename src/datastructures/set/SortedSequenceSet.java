package datastructures.set;

import datastructures.BinarySearch;
import datastructures.BinarySearch.IndexToInsertInOrder;
import datastructures.MergeSort;
import datastructures.sequence.Sequence;
import datastructures.sequence.DoubleEdgedLinearSequence;

public class SortedSequenceSet<T extends Comparable<T>> implements Set<T> {

    private final Sequence<T> sequence;

    /**
     * O(1)
     */
    public SortedSequenceSet() {
        sequence = new DoubleEdgedLinearSequence<>();
    }

    /**
     * O(1)
     */
    public SortedSequenceSet(int initialCapacity) {
        sequence = new DoubleEdgedLinearSequence<>(initialCapacity);
    }

    /**
     * O(n*log(n))
     */
    public SortedSequenceSet(T[] values) {
        sequence = new DoubleEdgedLinearSequence<>(values);
        MergeSort.sort(sequence);
    }

    /**
     * O(log(n))
     * Checks if value exists within set
     */
    @Override
    public boolean contains(T value) {
        return BinarySearch.search(sequence, value) > -1;
    }

    /**
     * O(log(n)) finds what position to insert and insert it
     */
    @Override
    public void add(T value) {
        IndexToInsertInOrder idx = BinarySearch.findIndexToStaySorted(sequence, value);
        if (idx.match()) { // if it's a match, override
            sequence.set(idx.index(), value);
        } else { // else insert
            sequence.insertAt(idx.index(), value);
        }
    }

    /**
     * O(log(n)) deletes entry if it exists
     */
    @Override
    public void delete(T value) {
        int idx = BinarySearch.search(sequence, value);
        if (idx == -1) {
            return;
        }

        sequence.deleteAt(idx);
    }

    /**
     * O(1)
     */
    @Override
    public T first() {
        return sequence.first();
    }

    /**
     * O(1)
     */
    @Override
    public T last() {
        return sequence.last();
    }

    /**
     * O(log(n))
     */
    @Override
    public T findPrevious(T value) {
        int current = BinarySearch.search(sequence, value);
        if (current < 1) {
            return null;
        }

        return sequence.at(current - 1);
    }

    /**
     * O(log(n))
     */
    @Override
    public T findNext(T value) {
        int current = BinarySearch.search(sequence, value);
        if (current < 0 || current >= size() - 1) {
            return null;
        }

        return sequence.at(current + 1);
    }

    @Override
    public T find(T value) {
        int pos = BinarySearch.search(sequence, value);
        if(pos < 0) {
            return null;
        }

        return sequence.at(pos);
    }

    /**
     * O(1)
     */
    @Override
    public int size() {
        return sequence.size();
    }

    /**
     * O(n)
     */
    @Override
    public String toString() {
        return sequence.toString();
    }

}
