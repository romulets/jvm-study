package datastructures;

public class BinarySearch {

    public record IndexToInsertInOrder(boolean match, int index) {
    }

    /**
     * O(log(n))
     *
     * @return Return a pair of if it's an exact match and what index must be updated
     */
    public static <T extends Comparable<T>> IndexToInsertInOrder findIndexToStaySorted(DoubleEdgedLinearArray<T> array, T value) {
        if (array.size() == 0) {
            return new IndexToInsertInOrder(false, 0);
        }

        int compareFirst = value.compareTo(array.first());
        if (compareFirst < 0) {
            return new IndexToInsertInOrder(false, 0);
        }

        if (compareFirst == 0) {
            return new IndexToInsertInOrder(true, 0);
        }

        int compareLast = value.compareTo(array.last());
        if (compareLast > 0) {
            return new IndexToInsertInOrder(false, array.size());
        }

        if (compareLast == 0) {
            return new IndexToInsertInOrder(true, array.size() - 1);
        }


        int begin = 0;
        int end = array.size() - 1;

        while (end >= begin) {
            int half = Math.ceilDiv(end - begin, 2) + begin;

            int compare = array.at(half).compareTo(value);
            if (compare == 0) { // found
                return new IndexToInsertInOrder(true, half);
            }

            if (compare > 0 && // if half is larger
                    half - 1 > 0 && // we are not at the edge
                    array.at(half - 1).compareTo(value) < 0) { // previous value is smaller than value
                // Then value should be inserted here
                return new IndexToInsertInOrder(false, half);
            }

            if (compare < 0 && // if half is less
                    half + 1 < array.size() && // we are not at the edge
                    array.at(half + 1).compareTo(value) > 0) { // next value is greater than value
                // Then value should be inserted here
                return new IndexToInsertInOrder(false, half + 1);
            }

            // half is larger that value,
            // thus the result like is at first half
            if (compare > 0) {
                end = half - 1;
            } else {
                begin = half + 1;
            }
        }

        // Should never happen
        return new IndexToInsertInOrder(false, -1);
    }

    /**
     * O(log(n))
     * @return index of value. -1 if not found
     */
    public static <T extends Comparable<T>> int search(DoubleEdgedLinearArray<T> array, T value) {
        int begin = 0;
        int end = array.size() - 1;

        while (end >= begin) {
            int half = Math.ceilDiv(end - begin, 2) + begin;

            int compare = array.at(half).compareTo(value);
            if (compare == 0) { // found
                return half;
            }

            // half is larger that value,
            // thus the result like is at first half
            if (compare > 0) {
                end = half - 1;
            } else {
                begin = half + 1;
            }
        }

        return -1;
    }

    /**
     * O(log(n))
     *
     * @return index of value. -1 if not found
     */
    public static <T extends Comparable<T>> int search(T[] array, T value) {
        int begin = 0;
        int end = array.length - 1;

        while (end >= begin) {
            int half = Math.ceilDiv(end - begin, 2) + begin;

            int compare = array[half].compareTo(value);
            if (compare == 0) { // found
                return half;
            }

            // half is larger that value,
            // thus the result like is at first half
            if (compare > 0) {
                end = half - 1;
            } else {
                begin = half + 1;
            }
        }

        return -1;
    }
}
