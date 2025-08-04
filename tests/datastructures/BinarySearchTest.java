package datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTest {

    @Test
    void search_half() {
        Integer[] array = new Integer[]{1, 2, 3};

        assertEquals(1, BinarySearch.search(array, 2));
    }

    @Test
    void search_edges() {
        Integer[] array = new Integer[]{1, 2, 3};

        assertEquals(2, BinarySearch.search(array, 3));
        assertEquals(0, BinarySearch.search(array, 1));
    }

    @Test
    void search_even() {
        Integer[] array = new Integer[]{1, 2, 3, 4};

        assertEquals(0, BinarySearch.search(array, 1));
        assertEquals(1, BinarySearch.search(array, 2));
        assertEquals(2, BinarySearch.search(array, 3));
        assertEquals(3, BinarySearch.search(array, 4));
    }

    @Test
    void search_two() {
        Integer[] array = new Integer[]{1, 2};

        assertEquals(0, BinarySearch.search(array, 1));
        assertEquals(1, BinarySearch.search(array, 2));
    }

    @Test
    void search_one() {
        Integer[] array = new Integer[]{1};

        assertEquals(0, BinarySearch.search(array, 1));
    }

    @ParameterizedTest()
    @MethodSource("sortedArgumentProvider")
    public void search_many(Integer[] input) {
        for (int i = 0; i < input.length - 1; i++) {
            assertEquals(i, BinarySearch.search(input, i));
        }
    }

    @ParameterizedTest()
    @MethodSource("sortedArgumentProvider")
    public void search_doubleEdgedLinearArray_many(Integer[] input) {
        DoubleEdgedLinearArray<Integer> array = new DoubleEdgedLinearArray<>(input.length * 2);
        for (Integer i : input) {
            array.insertLast(i);
        }

        for (int i = 0; i < array.size() - 1; i++) {
            assertEquals(i, BinarySearch.search(array, i));
        }
    }


    static Stream<Arguments> sortedArgumentProvider() {
        int times = 1000;
        Arguments[] arguments = new Arguments[times];
        for (int i = 0; i < times; i++) {
            arguments[i] = Arguments.of((Object) generateSortedArray(i + 1));
        }

        return Arrays.stream(arguments);
    }

    static Integer[] generateSortedArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

}