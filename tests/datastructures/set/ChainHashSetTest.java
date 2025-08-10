package datastructures.set;

import datastructures.sequence.LinkedSequence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ChainHashSetTest {

    @Test
    void find() {
        ChainHashSet<String> set = new ChainHashSet<>(new String[]{"b", "d", "a", "c"});

        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
        assertTrue(set.contains("d"));
        assertFalse(set.contains("e"));
        assertEquals(set.size(), 4);
    }


    @Test
    void add() {
        ChainHashSet<String> set = new ChainHashSet<>(5);

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
    void add_growth() {
        ChainHashSet<Integer> set = new ChainHashSet<>(2);
        for (int i = 0; i < 10_000; i++) {
            set.add(i);
        }

        assertEquals(set.capacity(), 1024);
        assertEquals(set.size(), 10_000);
        set.add(1);
        assertEquals(set.capacity(), 1024);
        assertEquals(set.size(), 10_000);

        for (int i = 0; i < 241; i++) {
            set.add(i * -1);
        }

        assertEquals(set.capacity(), 2048);
        assertEquals(set.size(), 10_240);

    }


    @Test
    void delete() {
        ChainHashSet<String> set = new ChainHashSet<>(new String[]{"b", "d", "a", "c", "f", "e"});
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
        ChainHashSet<String> set = new ChainHashSet<>(new String[]{"b", "d", "a", "c", "f", "e"});
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

    @Test
    void findPrevious() {
        ChainHashSet<String> set = new ChainHashSet<>(2);
        for (String val : new String[]{"b", "d", "a", "c", "f", "e"}) {
            set.add(val);
        }

        String[] expectedSequence = new String[]{"f", "d", "b", "e", "c", "a"};

        String last = set.last();
        for (String val : expectedSequence) {
            assertEquals(last, val);
            last = set.findPrevious(last);
        }
    }

    @Test
    void findNext() {
        ChainHashSet<String> set = new ChainHashSet<>(new String[]{"b", "d", "a", "c", "f", "e"});
        String[] expectedSequence = new String[]{"a", "b", "c", "d", "e", "f"};

        String next = set.first();
        for (String val : expectedSequence) {
            assertEquals(next, val);
            next = set.findNext(next);
        }
    }

    @Test
    void findNext_withCollision() {
        ChainHashSet<String> set = new ChainHashSet<>(2);
        String[] toAdd = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
        for (String s : toAdd) {
            set.add(s);
        }

        String[] expectedSequence = new String[]{"a", "c", "e", "g", "b", "d", "f", "h"};

        String next = set.first();
        int i = 0;
        while (next != null) {
            assertEquals(next, expectedSequence[i]);
            next = set.findNext(next);
            i++;
        }
    }

    @Test
    void findNext_withSpaceInBetween() {
        ChainHashSet<String> set = new ChainHashSet<>(48);
        set.add("d");
        set.add("w");

        assertEquals(set.first(), "w");
        assertEquals(set.findNext(set.first()), "d");
    }

    @ParameterizedTest
    @MethodSource("outOfOrderArgumentsProvider")
    void testGrowth(Integer[] input) {
        ChainHashSet<Integer> set = new ChainHashSet<>();
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
        int times = 500;
        Arguments[] arguments = new Arguments[times];
        for (int i = 0; i < times; i++) {
            arguments[i] = Arguments.of((Object) generateArray(i + 1));
        }

        return Arrays.stream(arguments);
    }

    static Integer[] generateArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

}
