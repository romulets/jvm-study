package datastructures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortedArraySetTest {

    @Test
    void find() {
        SortedArraySet<String> set = new SortedArraySet<>(new String[]{"b", "d", "a", "c"});

        assertEquals(set.find("a"), 0);
        assertEquals(set.find("b"), 1);
        assertEquals(set.find("c"), 2);
        assertEquals(set.find("d"), 3);
        assertEquals(set.find("e"), -1);
    }
}
