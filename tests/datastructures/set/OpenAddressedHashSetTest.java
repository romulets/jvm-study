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

public class OpenAddressedHashSetTest {

    @Test
    void find() {
        OpenAddressedHashSet<String> set = new OpenAddressedHashSet<>(new String[]{"b", "d", "a", "c"});

        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
        assertTrue(set.contains("d"));
        assertFalse(set.contains("e"));
        assertEquals(set.size(), 4);
    }


    @Test
    void add() {
        OpenAddressedHashSet<String> set = new OpenAddressedHashSet<>(5);

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
        OpenAddressedHashSet<Integer> set = new OpenAddressedHashSet<>(2);
        for (int i = 0; i < 10_000; i++) {
            set.add(i);
        }

        assertEquals(set.capacity(), 16384);
        assertEquals(set.size(), 10_000);
        set.add(1);
        assertEquals(set.capacity(), 16384);
        assertEquals(set.size(), 10_000);

        for (int i = 0; i < 241; i++) {
            set.add(i * -1);
        }

        assertEquals(set.capacity(), 32768); // high value due to clustering
        assertEquals(set.size(), 10_240);

    }


    @Test
    void delete() {
        OpenAddressedHashSet<String> set = new OpenAddressedHashSet<>(new String[]{"b", "d", "a", "c", "f", "e"});
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
        assertFalse(set.contains("d"));
        assertTrue(set.contains("e"));
        assertTrue(set.contains("f"));

        set.delete("b");
        assertEquals(set.size(), 4);
        assertTrue(set.contains("a"));
        assertFalse(set.contains("b"));
        assertTrue(set.contains("c"));
        assertFalse(set.contains("d"));
        assertTrue(set.contains("e"));
        assertTrue(set.contains("f"));

        set.delete("f");
        assertEquals(set.size(), 3);
        assertTrue(set.contains("a"));
        assertFalse(set.contains("b"));
        assertTrue(set.contains("c"));
        assertFalse(set.contains("d"));
        assertTrue(set.contains("e"));
        assertFalse(set.contains("f"));

        set.delete("a");
        assertEquals(set.size(), 2);
        assertFalse(set.contains("a"));
        assertFalse(set.contains("b"));
        assertTrue(set.contains("c"));
        assertFalse(set.contains("d"));
        assertTrue(set.contains("e"));
        assertFalse(set.contains("f"));

        set.delete("e");
        assertEquals(set.size(), 1);
        assertFalse(set.contains("a"));
        assertFalse(set.contains("b"));
        assertTrue(set.contains("c"));
        assertFalse(set.contains("d"));
        assertFalse(set.contains("e"));
        assertFalse(set.contains("f"));

        set.delete("c");
        assertEquals(set.size(), 0);
        assertFalse(set.contains("a"));
        assertFalse(set.contains("b"));
        assertFalse(set.contains("c"));
        assertFalse(set.contains("d"));
        assertFalse(set.contains("e"));
        assertFalse(set.contains("f"));
    }

    @Test
    void findLast_findFirst() {
        OpenAddressedHashSet<String> set = new OpenAddressedHashSet<>(new String[]{"b", "d", "a", "c", "f", "e"});
        assertEquals(set.first(), "e");
        assertEquals(set.last(), "d");

        set.delete("e");
        set.delete("d");

        assertEquals(set.first(), "f");
        assertEquals(set.last(), "c");

        set.delete("f");
        set.delete("c");

        assertEquals(set.first(), "a");
        assertEquals(set.last(), "b");
    }

    @Test
    void findPrevious() {
        OpenAddressedHashSet<String> set = new OpenAddressedHashSet<>(new String[]{"b", "d", "a", "c", "f", "e"});
        String[] expectedSequence = new String[]{"d", "c", "b", "a", "f", "e"};

        String last = set.last();
        for (String val : expectedSequence) {
            assertEquals(last, val);
            last = set.findPrevious(last);
        }

    }

    @Test
    void findNext() {
        OpenAddressedHashSet<String> set = new OpenAddressedHashSet<>(new String[]{"b", "d", "a", "c", "f", "e"});
        String[] expectedSequence = new String[]{"e", "f", "a", "b", "c", "d"};

        String next = set.first();
        for (String val : expectedSequence) {
            assertEquals(next, val);
            next = set.findNext(next);
        }
    }

    @Test
    void findNext_withCollision() {
        OpenAddressedHashSet<String> set = new OpenAddressedHashSet<>(2);
        String[] toAdd = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
        for (String s : toAdd) {
            set.add(s);
        }

        String[] expectedSequence = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};

        String next = set.first();
        for (String val : expectedSequence) {
            assertEquals(next, val);
            next = set.findNext(next);
        }
    }

    @Test
    void findNext_withSpaceInBetween() {
        OpenAddressedHashSet<String> set = new OpenAddressedHashSet<>(48);
        set.add("d");
        set.add("w");

        assertEquals(set.first(), "w");
        assertEquals(set.findNext(set.first()), "d");
    }

    @ParameterizedTest
    @MethodSource("outOfOrderArgumentsProvider")
    void testGrowth(Integer[] input) {
        OpenAddressedHashSet<Integer> set = new OpenAddressedHashSet<>();
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
