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


    @Test
    void delete() {
        SortedArraySet<String> set = new SortedArraySet<>(new String[]{"b", "d", "a", "c", "f", "e"});
        assertEquals(set.size(), 6);
        assertEquals(set.find("a"), 0);
        assertEquals(set.find("b"), 1);
        assertEquals(set.find("c"), 2);
        assertEquals(set.find("d"), 3);
        assertEquals(set.find("e"), 4);
        assertEquals(set.find("f"), 5);

        set.delete("h");
        assertEquals(set.size(), 6);

        set.delete("d");
        assertEquals(set.size(), 5);
        assertEquals(set.find("a"), 0);
        assertEquals(set.find("b"), 1);
        assertEquals(set.find("c"), 2);
        assertEquals(set.find("e"), 3);
        assertEquals(set.find("f"), 4);

        set.delete("b");
        assertEquals(set.size(), 4);
        assertEquals(set.find("a"), 0);
        assertEquals(set.find("c"), 1);
        assertEquals(set.find("e"), 2);
        assertEquals(set.find("f"), 3);

        set.delete("f");
        assertEquals(set.size(), 3);
        assertEquals(set.find("a"), 0);
        assertEquals(set.find("c"), 1);
        assertEquals(set.find("e"), 2);

        set.delete("a");
        assertEquals(set.size(), 2);
        assertEquals(set.find("c"), 0);
        assertEquals(set.find("e"), 1);

        set.delete("e");
        assertEquals(set.size(), 1);
        assertEquals(set.find("c"), 0);

        set.delete("c");
        assertEquals(set.size(), 0);
    }

    @Test
    void findLast_findFirst() {
        SortedArraySet<String> set = new SortedArraySet<>(new String[]{"b", "d", "a", "c", "f", "e"});
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
        SortedArraySet<String> set = new SortedArraySet<>(new String[]{"b", "d", "a", "c", "f", "e"});
        String[] expectedSequence = new String[]{"f", "e", "d", "c", "b", "a"};

        String last = set.last();
        int i = 0;
        while(last != null) {
            assertEquals(last, expectedSequence[i]);
            last = set.findPrevious(last);
            i++;
        }

    }

    @Test
    void findNext() {
        SortedArraySet<String> set = new SortedArraySet<>(new String[]{"b", "d", "a", "c", "f", "e"});
        String[] expectedSequence = new String[]{"a", "b", "c", "d", "e", "f"};

        String next = set.first();
        int i = 0;
        while(next != null) {
            assertEquals(next, expectedSequence[i]);
            next = set.findNext(next);
            i++;
        }

    }
}
