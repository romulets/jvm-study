package datastructures;

import datastructures.BinarySearch.IndexToInsertInOrder;
import datastructures.array.DoubleEdgedLinearArray;
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

    @Test
    void findIndexToStaySorted_empty() {
        DoubleEdgedLinearArray<Integer> array = new DoubleEdgedLinearArray<>();
        IndexToInsertInOrder idx = BinarySearch.findIndexToStaySorted(array, 3);
        assertFalse(idx.match());
        assertEquals(idx.index(), 0);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("findIndexToStaySorted")
    public void findIndexToStaySorted(String name, Integer[] input, int searchValue, IndexToInsertInOrder expected) {
        DoubleEdgedLinearArray<Integer> array = new DoubleEdgedLinearArray<>(input);
        IndexToInsertInOrder idx = BinarySearch.findIndexToStaySorted(array, searchValue);
        assertEquals(expected, idx);
    }

    static Stream<Arguments> findIndexToStaySorted() {
        return Stream.of(
                Arguments.of("1 left edge", new Integer[]{1}, 0, new IndexToInsertInOrder(false, 0)),
                Arguments.of("1 match", new Integer[]{1}, 1, new IndexToInsertInOrder(true, 0)),
                Arguments.of("1 right edge", new Integer[]{1}, 2, new IndexToInsertInOrder(false, 1)),

                Arguments.of("2 left edge", new Integer[]{4, 8}, 2, new IndexToInsertInOrder(false, 0)),
                Arguments.of("2 match right", new Integer[]{4, 8}, 4, new IndexToInsertInOrder(true, 0)),
                Arguments.of("2 middle", new Integer[]{4, 8}, 6, new IndexToInsertInOrder(false, 1)),
                Arguments.of("2 match left", new Integer[]{4, 8}, 8, new IndexToInsertInOrder(true, 1)),
                Arguments.of("2 right edge", new Integer[]{4, 8}, 12, new IndexToInsertInOrder(false, 2)),

                Arguments.of("3 left edge", new Integer[]{4, 8, 12}, 2, new IndexToInsertInOrder(false, 0)),
                Arguments.of("3 left match", new Integer[]{4, 8, 12}, 4, new IndexToInsertInOrder(true, 0)),
                Arguments.of("3 left insert", new Integer[]{4, 8, 12}, 6, new IndexToInsertInOrder(false, 1)),
                Arguments.of("3 middle match", new Integer[]{4, 8, 12}, 8, new IndexToInsertInOrder(true, 1)),
                Arguments.of("3 right insert", new Integer[]{4, 8, 12}, 10, new IndexToInsertInOrder(false, 2)),
                Arguments.of("3 right middle", new Integer[]{4, 8, 12}, 12, new IndexToInsertInOrder(true, 2)),
                Arguments.of("3 right edge", new Integer[]{4, 8, 12}, 14, new IndexToInsertInOrder(false, 3)),

                Arguments.of("4 no match 0", new Integer[]{4, 8, 12, 16}, 2, new IndexToInsertInOrder(false, 0)),
                Arguments.of("4 match 0", new Integer[]{4, 8, 12, 16}, 4, new IndexToInsertInOrder(true, 0)),
                Arguments.of("4 no match 1", new Integer[]{4, 8, 12, 16}, 6, new IndexToInsertInOrder(false, 1)),
                Arguments.of("4 match 1", new Integer[]{4, 8, 12, 16}, 8, new IndexToInsertInOrder(true, 1)),
                Arguments.of("4 no match 2", new Integer[]{4, 8, 12, 16}, 10, new IndexToInsertInOrder(false, 2)),
                Arguments.of("4 match 2", new Integer[]{4, 8, 12, 16}, 12, new IndexToInsertInOrder(true, 2)),
                Arguments.of("4 no match 3", new Integer[]{4, 8, 12, 16}, 14, new IndexToInsertInOrder(false, 3)),
                Arguments.of("4 match 3", new Integer[]{4, 8, 12, 16}, 16, new IndexToInsertInOrder(true, 3)),
                Arguments.of("4 not match 4", new Integer[]{4, 8, 12, 16}, 18, new IndexToInsertInOrder(false, 4)),

                Arguments.of("5 no match 0", new Integer[]{4, 8, 12, 16, 20}, 2, new IndexToInsertInOrder(false, 0)),
                Arguments.of("5 match 0", new Integer[]{4, 8, 12, 16, 20}, 4, new IndexToInsertInOrder(true, 0)),
                Arguments.of("5 no match 1", new Integer[]{4, 8, 12, 16, 20}, 6, new IndexToInsertInOrder(false, 1)),
                Arguments.of("5 match 1", new Integer[]{4, 8, 12, 16, 20}, 8, new IndexToInsertInOrder(true, 1)),
                Arguments.of("5 no match 2", new Integer[]{4, 8, 12, 16, 20}, 10, new IndexToInsertInOrder(false, 2)),
                Arguments.of("5 match 2", new Integer[]{4, 8, 12, 16, 20}, 12, new IndexToInsertInOrder(true, 2)),
                Arguments.of("5 no match 3", new Integer[]{4, 8, 12, 16, 20}, 14, new IndexToInsertInOrder(false, 3)),
                Arguments.of("5 match 3", new Integer[]{4, 8, 12, 16, 20}, 16, new IndexToInsertInOrder(true, 3)),
                Arguments.of("5 not match 4", new Integer[]{4, 8, 12, 16, 20}, 18, new IndexToInsertInOrder(false, 4)),
                Arguments.of("5 match 4", new Integer[]{4, 8, 12, 16, 20}, 20, new IndexToInsertInOrder(true, 4)),
                Arguments.of("5 no match 5", new Integer[]{4, 8, 12, 16, 20}, 22, new IndexToInsertInOrder(false, 5))

        );
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