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


    @Test
    void insert() {
        SortedArraySet<String> set = new SortedArraySet<>(5);

        set.insert("d");
        assertEquals(set.find("d"), 0);

        set.insert("a");
        assertEquals(set.find("a"), 0);
        assertEquals(set.find("d"), 1);

        set.insert("c");
        assertEquals(set.find("a"), 0);
        assertEquals(set.find("c"), 1);
        assertEquals(set.find("d"), 2);

        set.insert("b");
        assertEquals(set.find("a"), 0);
        assertEquals(set.find("b"), 1);
        assertEquals(set.find("c"), 2);
        assertEquals(set.find("d"), 3);

        set.insert("f");
        assertEquals(set.find("a"), 0);
        assertEquals(set.find("b"), 1);
        assertEquals(set.find("c"), 2);
        assertEquals(set.find("d"), 3);
        assertEquals(set.find("f"), 4);

        set.insert("e");
        assertEquals(set.find("a"), 0);
        assertEquals(set.find("b"), 1);
        assertEquals(set.find("c"), 2);
        assertEquals(set.find("d"), 3);
        assertEquals(set.find("e"), 4);
        assertEquals(set.find("f"), 5);
    }
}
