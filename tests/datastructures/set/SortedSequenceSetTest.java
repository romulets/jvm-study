package datastructures.set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SortedSequenceSetTest extends SetTestCases {

    protected SortedSequenceSetTest() {
        super(SortedSequenceSet::new, SortedSequenceSet::new, SortedSequenceSet::new);
    }
    
    @Test
    void findPrevious() {
        SortedSequenceSet<String> set = new SortedSequenceSet<>(new String[]{"b", "d", "a", "c", "f", "e"});
        String[] expectedSequence = new String[]{"f", "e", "d", "c", "b", "a"};

        String last = set.last();
        int i = 0;
        while (last != null) {
            assertEquals(last, expectedSequence[i]);
            last = set.findPrevious(last);
            i++;
        }

    }

    @Test
    void findNext() {
        SortedSequenceSet<String> set = new SortedSequenceSet<>(new String[]{"b", "d", "a", "c", "f", "e"});
        String[] expectedSequence = new String[]{"a", "b", "c", "d", "e", "f"};

        String next = set.first();
        int i = 0;
        while (next != null) {
            assertEquals(next, expectedSequence[i]);
            next = set.findNext(next);
            i++;
        }
    }

}
