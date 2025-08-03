package datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MergeSortTest {

    @Test
    public void sort_2() {
        Integer[] input = new Integer[]{2, 1};
        MergeSort.sort(input);
        assertArrayEquals(new Integer[]{1, 2}, input, Arrays.toString(input));
    }

    @Test
    public void sort_3() {
        Integer[] input = new Integer[]{6, 4, 2};
        MergeSort.sort(input);
        assertArrayEquals(new Integer[]{2, 4, 6}, input, Arrays.toString(input));
    }

    @Test
    public void sort_4() {
        Integer[] input = new Integer[]{10, 6, 4, 2};
        MergeSort.sort(input);
        assertArrayEquals(new Integer[]{2, 4, 6, 10}, input, Arrays.toString(input));
    }

    @Test
    public void sort_6() {
        Integer[] input = new Integer[]{10, 6, 12, 4, 2, 16};
        MergeSort.sort(input);
        assertArrayEquals(new Integer[]{2, 4, 6, 10, 12, 16}, input, Arrays.toString(input));
    }

    @Test
    public void sort_10() {
        Integer[] input = new Integer[]{10, 20, 6, 12, 30, 27, 4, 15, 2, 16};
        MergeSort.sort(input);
        assertArrayEquals(new Integer[]{2, 4, 6, 10, 12, 15, 16, 20, 27, 30,}, input);
    }

    @ParameterizedTest(name = "small name")
    @MethodSource("everySizeArgumentProvider")
    public void sort_random(Integer[] input) {
        MergeSort.sort(input);
        for (int i = 0; i < input.length - 1; i++) {
            assertTrue(input[i] <=  input[i + 1],
                    "Pos ["+i+"] array failed to sort " + Arrays.toString(input));
        }
    }

    static Stream<Arguments> everySizeArgumentProvider() {
        int times = 2_000;
        Arguments[] arguments = new Arguments[times];
        for (int  i = 0; i < times; i++) {
            arguments[i] = Arguments.of((Object) generateArray(i + 1));
        }

        return Arrays.stream(arguments);
    }
    static Integer[] generateArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = 2_000 - i; // always out of order
        }
        return array;
    }
}
