package datastructures;

import java.util.Arrays;

public class MergeSort {

    /**
     * ATTEMPT BY HEART
     * O(n*log(n)) - Takes a list a merge sorts it.
     *
     * It does by splitting the array in multiple
     * smaller problems and merge from smallest
     * (k=2) and grows on factor of 2 until
     * everything is merged.
     * @param array
     */
    public static <T extends Comparable<T>> void sort(T[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        int chunkSize = 1;
        int i = 0;
//        int iterations = 0;

        while (i < array.length) {
//            iterations++;
            int list1Begin = i;
            int list1End = Math.min(list1Begin + chunkSize, array.length);
            int list2Begin = i + chunkSize;
            int list2End = Math.min(list2Begin + chunkSize, array.length);

            // if the second list needs to be merged into the first
            if (list2Begin < array.length) {
                T[] firstHalf = Arrays.copyOfRange(array, list1Begin, list1End);    // O(n)
                T[] secondHalf = Arrays.copyOfRange(array, list2Begin, list2End);   // O(n)

                int ptr1 = 0;
                int ptr2 = 0;
                for (int actualPos = list1Begin; actualPos < list2End; actualPos++) { // O(chunkSize * 2) -> O(n)
//                    iterations++;
                    if (ptr1 == firstHalf.length) { // we exhausted firstHalf already
                        array[actualPos] = secondHalf[ptr2]; // set pt2;
                        ptr2++;
                        continue;
                    }

                    if (ptr2 == secondHalf.length) { // we exhausted secondHalf already
                        array[actualPos] = firstHalf[ptr1]; // set pt1;
                        ptr1++;
                        continue;
                    }

                    // If ptr1 is larger than pt2
                    if(firstHalf[ptr1].compareTo(secondHalf[ptr2]) > 0) {
                        array[actualPos] = secondHalf[ptr2]; // set pt2;
                        ptr2++;
                    } else {
                        array[actualPos] = firstHalf[ptr1]; // set pt1;
                        ptr1++;
                    }
                }
            }


            // If we are about to finish the loop (i + chunkSize * 2 > array.length)
            // And we have not yet merged the whole array
            // Reset i and increase chunk size
            if (i + chunkSize * 2 >= array.length && chunkSize * 2 < array.length) {
                i = 0;
                chunkSize *= 2;
            } else {
                i += chunkSize * 2;
            }
        }

//        System.out.print(array.length + ";" + iterations + "\n");
    }

    /**
     * O(n*log(n)) - Takes a list a merge sorts it.
     *
     * It does by splitting the array in multiple
     * smaller problems and merge from smallest
     * (k=2) and grows on factor of 2 until
     * everything is merged.
     * @param array
     */
    public static void sortByBook(Integer[] array) {
        if (array == null || array.length < 2) {
            return;
        }

//        int it = 0;

        int chunkSize = 1;
        while (chunkSize < array.length) {
//            it++;
            int i = 0;
            while (i < array.length) {
//                it++;
                int start1 = i;
                int end1 = i + chunkSize - 1;
                int start2 = i + chunkSize;
                int end2 = Math.min(i + (2 * chunkSize) - 1, array.length - 1);
                int totalSize = end2 - start1 + 1;

                if (start2 >= array.length) {
                    break;
                }

//                Integer[] tmp = merge(array, start1, end1, start2, end2);

                Integer[] tmp = new Integer[totalSize];
                int index = 0;

                // While we still have docs to look
                while (start1 <= end1 && start2 <= end2) {
//                    it++;
                    if (array[start1] > array[start2]) {
                        tmp[index] = array[start2];
                        start2++;
                    } else {
                        tmp[index] = array[start1];
                        start1++;
                    }

                    index++;
                }

                // exhaust n1
                while (start1 <= end1) {
//                    it++;
                    tmp[index] = array[start1];
                    index++;
                    start1++;
                }

                // exhaust n2
                while (start2 <= end2) {
//                    it++;
                    tmp[index] = array[start2];
                    index++;
                    start2++;
                }

                for (int j = 0; j < totalSize; j++) {
                    array[i+j] = tmp[j];
                }

                i += 2 * chunkSize;
            }
            chunkSize *= 2;
        }

//        System.out.print(array.length + ";" + it + "\n");
    }


    // Attempt to do without consulting, WRONG.
    // I ended up doing a swap instead of merge swap
    // in attempt of do in mem O(1). However, that is clearly
    // not efficient as I go through the whole list multiple times.
    // It's just a swap sort broken in multiple places.
    // I believe the complexity of this one is O(nˆ2).
    // Upon consultation I confirmed that merge sort doesn't have constant memory.
    private static <T extends Comparable<T>> void not_merge_sort(T[] array) {
        if (array == null || array.length < 2) {
            return;
        }

        // O(n/2)
        // for each two pairs, sort them
        for(int firstIndex = 0; firstIndex < array.length; firstIndex += 2) {
            // make sure we are not going out of bounds
            int lastIndex = Math.min(firstIndex + 1, array.length - 1);

            // If j is larger that j+1, we need to swap
            if (array[firstIndex].compareTo(array[lastIndex]) > 0) {
                // swap them
                T tmp = array[firstIndex];
                array[firstIndex] = array[lastIndex];
                array[lastIndex] = tmp;
            }
        }

        // Start chunk size on 2
        int chunkSize = 2;

        int beginChunk = 0;
        int endChunk = Math.min(beginChunk + (chunkSize * 2), array.length);
        // While we have not yet seen the whole array
        // O(n/2)
        while (chunkSize < array.length) {
            //merge sort inplace smaller arrays
            // [3, 4, 1, 2]
            //  is 3 smaller than 4? -> yes, keep 3
            //  is 3 smaller than 1? -> no, swap 1 with 3
            // [1, 4, 3, 2]
            //  is 1 smaller than 2? -> yes, keep 1
            //  -- end of loop 1
            //  is 4 smaller than 3? -> no, swap 3 with 4
            // [1, 3, 4, 2]
            //  is 3 smaller than 2? -> no, swap 2 with 3
            // [1, 2, 4, 3]
            // -- end of loop 2
            //   is 4 smaller than 3? -> no, swap 3 with 4
            //  [1, 2, 3, 4]
            // IS THIS A MERGE SORT??? validate.

            // O(n * n/2) ????
            for (int i = beginChunk; i < endChunk; i++) {
                for (int j = i + 1; j < endChunk; j++) {
                    // Is i bigger than j?
                    if (array[i].compareTo(array[j]) > 0) {
                        // swap them
                        T tmp = array[i];
                        array[i] = array[j];
                        array[j] = tmp;
                    }
                }
            }

            if (endChunk < array.length) {
                beginChunk = endChunk + 1;
            } else {
                chunkSize *= 2;
                beginChunk = 0;
            }

            endChunk = Math.min(beginChunk + (chunkSize * 2), array.length);
        }
    }
}
