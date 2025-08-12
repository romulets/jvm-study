package datastructures.set;

import datastructures.sequence.LinkedSequence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ChainHashSetTest extends SetTestCases {

    protected ChainHashSetTest() {
        super(ChainHashSet::new, ChainHashSet::new, ChainHashSet::new);
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

}
