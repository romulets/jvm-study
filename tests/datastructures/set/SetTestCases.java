package datastructures.set;

import datastructures.sequence.LinkedSequence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class SetTestCases {

    private final Function<String[], Set<String>> stringArraySupplier;
    private final Function<Integer, Set<String>> initialSizeSupplier;
    private final Supplier<Set<Integer>> defaultSupplier;

    protected SetTestCases(Function<String[], Set<String>> stringArraySupplier, Function<Integer, Set<String>> initialSizeSupplier, Supplier<Set<Integer>> defaultSupplier) {
        this.stringArraySupplier = stringArraySupplier;
        this.initialSizeSupplier = initialSizeSupplier;
        this.defaultSupplier = defaultSupplier;
    }


    @Test
    void find() {
        Set<String> set = stringArraySupplier.apply(new String[]{"b", "d", "a", "c"});

        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
        assertTrue(set.contains("d"));
        assertFalse(set.contains("e"));
        assertEquals(set.size(), 4);
    }

    @Test
    void add() {
        Set<String> set = initialSizeSupplier.apply(5);

        set.add("d");
        set.add("d");
        set.add("d");
        set.add("d");
        assertTrue(set.contains("d"));
        assertEquals(set.size(), 1);

        set.add("a");
        assertTrue(set.contains("a"));
        assertTrue(set.contains("d"));
        assertEquals(set.size(), 2);

        set.add("c");
        assertTrue(set.contains("a"));
        assertTrue(set.contains("c"));
        assertTrue(set.contains("d"));
        assertEquals(set.size(), 3);

        set.add("b");
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
        assertTrue(set.contains("d"));
        assertEquals(set.size(), 4);

        set.add("f");
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
        assertTrue(set.contains("d"));
        assertTrue(set.contains("f"));
        assertEquals(set.size(), 5);

        set.add("e");
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
        assertTrue(set.contains("d"));
        assertTrue(set.contains("e"));
        assertTrue(set.contains("f"));
        assertEquals(set.size(), 6);
    }


    @Test
    void delete() {
        Set<String> set = stringArraySupplier.apply(new String[]{"b", "d", "a", "c", "f", "e"});
        assertEquals(set.size(), 6);
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
        assertTrue(set.contains("d"));
        assertTrue(set.contains("e"));
        assertTrue(set.contains("f"));

        set.delete("h");
        assertEquals(set.size(), 6);

        set.delete("d");
        assertEquals(set.size(), 5);
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
        assertTrue(set.contains("e"));
        assertTrue(set.contains("f"));

        set.delete("b");
        assertEquals(set.size(), 4);
        assertTrue(set.contains("a"));
        assertTrue(set.contains("c"));
        assertTrue(set.contains("e"));
        assertTrue(set.contains("f"));

        set.delete("f");
        assertEquals(set.size(), 3);
        assertTrue(set.contains("a"));
        assertTrue(set.contains("c"));
        assertTrue(set.contains("e"));

        set.delete("a");
        assertEquals(set.size(), 2);
        assertTrue(set.contains("c"));
        assertTrue(set.contains("e"));

        set.delete("e");
        assertEquals(set.size(), 1);
        assertTrue(set.contains("c"));

        set.delete("c");
        assertEquals(set.size(), 0);
    }

    @Test
    void findLast_findFirst() {
        Set<String> set = stringArraySupplier.apply(new String[]{"b", "d", "a", "c", "f", "e"});
        assertEquals(set.first(), "a");
        assertEquals(set.last(), "f");

        set.delete("a");
        set.delete("f");

        assertEquals(set.first(), "b");
        assertEquals(set.last(), "e");

        set.delete("c");
        set.delete("d");

        assertEquals(set.first(), "b");
        assertEquals(set.last(), "e");
    }

    @ParameterizedTest
    @MethodSource("outOfOrderArgumentsProvider")
    void testMany(Integer[] input) {
        Set<Integer> set = defaultSupplier.get();
        LinkedSequence<Integer> allSeen = new LinkedSequence<>(input);

        for (int i = 0; i < input.length; i++) {
            set.add(i);
        }

        Integer val = set.first();
        while (val != null) {
            // VERY COSTLY REMOVAL
            for (int i = 0; i < allSeen.size(); i++) {
                if (Objects.equals(allSeen.at(i), val)) {
                    allSeen.deleteAt(i);
                    break;
                }
            }

            val = set.findNext(val);
        }

        assertEquals(allSeen.size(), 0);
        assertEquals(set.size(), input.length);
    }


    static Stream<Arguments> outOfOrderArgumentsProvider() {
        int[] sizes = new int[]{1,100,200,300,400,500,600,700,800,900,1000};
        Arguments[] arguments = new Arguments[sizes.length];
        int i = 0;
        for (int size : sizes) {
            arguments[i] = Arguments.of((Object) generateArray(size));
            i++;
        }

        return Arrays.stream(arguments);
    }

    static Integer[] generateArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        shuffleArray(array);
        return array;
    }

    static void shuffleArray(Integer[] array) {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            if (index != i) {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }
}
