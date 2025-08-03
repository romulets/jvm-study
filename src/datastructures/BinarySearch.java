package datastructures;

public class BinarySearch {

    /**
     * O(log(n))
     * @param array
     * @param value
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
     * @param array
     * @param value
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
