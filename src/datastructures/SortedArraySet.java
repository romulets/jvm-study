package datastructures;

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
     *
     * @param value
     * @return index
     */
    public int find(T value) {
        return BinarySearch.search(array, value);
    }

}
