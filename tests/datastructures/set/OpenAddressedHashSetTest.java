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

public class OpenAddressedHashSetTest extends SetTestCases {

    protected OpenAddressedHashSetTest() {
        super(OpenAddressedHashSet::new, OpenAddressedHashSet::new, OpenAddressedHashSet::new);
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
}
