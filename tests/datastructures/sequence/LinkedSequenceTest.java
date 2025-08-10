package datastructures.sequence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedSequenceTest {

    @Test
    void insertFirst() {
        LinkedSequence<String> array = new LinkedSequence<>();
        array.insertFirst("1");
        array.insertFirst("2");
        array.insertFirst("3");
        array.insertFirst("4");
        array.insertFirst("5");

        assertEquals(array.at(0), "5");
        assertEquals(array.at(1), "4");
        assertEquals(array.at(2), "3");
        assertEquals(array.at(3), "2");
        assertEquals(array.at(4), "1");

        assertEquals(array.size(), 5);
    }

    @Test
    void insertLast() {
        LinkedSequence<String> array = new LinkedSequence<>();
        array.insertLast("1");
        array.insertLast("2");
        array.insertLast("3");
        array.insertLast("4");
        array.insertLast("5");
        array.insertLast("6");

        assertEquals(array.at(0), "1");
        assertEquals(array.at(1), "2");
        assertEquals(array.at(2), "3");
        assertEquals(array.at(3), "4");
        assertEquals(array.at(4), "5");
        assertEquals(array.at(5), "6");

        assertEquals(array.size(), 6);
    }

    @Test
    void insertAt_outOfBound() {
        LinkedSequence<String> array = new LinkedSequence<>();
        assertThrows(IndexOutOfBoundsException.class, () -> array.insertAt(-1, "should fail"));
        assertThrows(IndexOutOfBoundsException.class, () -> array.insertAt(1, "should fail"));
    }

    @Test
    void insertAt() {
        LinkedSequence<String> array = new LinkedSequence<>();
        array.insertAt(0, "1");
        array.insertAt(1, "3");
        array.insertAt(1, "2");
        array.insertAt(0, "-1");
        array.insertAt(1, "0");

        assertEquals(array.at(0), "-1");
        assertEquals(array.at(1), "0");
        assertEquals(array.at(2), "1");
        assertEquals(array.at(3), "2");
        assertEquals(array.at(4), "3");

        assertEquals(array.size(), 5);
    }

    @Test
    void deleteAt_invalidIndex() {
        LinkedSequence<Integer> array = new LinkedSequence<>(new Integer[]{1, 2, 3});
        assertThrows(IndexOutOfBoundsException.class, () -> array.deleteAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.deleteAt(3));

        LinkedSequence<Integer> empty = new LinkedSequence<>();
        assertThrows(IndexOutOfBoundsException.class, () -> empty.deleteAt(0));
        assertThrows(IndexOutOfBoundsException.class, () -> empty.deleteAt(1));
    }

    /**
     * Also tests deleteFirst and deleteLast
     */
    @Test
    void deleteAt() {
        LinkedSequence<Integer> array = new LinkedSequence<>(new Integer[]{1, 2, 3, 4, 5});

        assertEquals(array.deleteAt(0), 1);
        assertEquals(array.size(), 4);
        assertEquals(array.at(0), 2);
        assertEquals(array.at(1), 3);
        assertEquals(array.at(2), 4);
        assertEquals(array.at(3), 5);


        assertEquals(array.deleteAt(3), 5);
        assertEquals(array.size(), 3);
        assertEquals(array.at(0), 2);
        assertEquals(array.at(1), 3);
        assertEquals(array.at(2), 4);

        assertEquals(array.deleteAt(1), 3);
        assertEquals(array.size(), 2);
        assertEquals(array.at(0), 2);
        assertEquals(array.at(1), 4);

        assertEquals(array.deleteAt(1), 4);
        assertEquals(array.size(), 1);
        assertEquals(array.at(0), 2);

        assertEquals(array.deleteAt(0), 2);
        assertEquals(array.size(), 0);
    }

    @Test
    void set() {
        LinkedSequence<Integer> array = new LinkedSequence<>(new Integer[]{1, 2, 3, 4, 5});

        array.set(0, 20);
        assertEquals(array.size(), 5);
        assertEquals(array.at(0), 20);
        assertEquals(array.at(1), 2);
        assertEquals(array.at(2), 3);
        assertEquals(array.at(3), 4);
        assertEquals(array.at(4), 5);

        array.set(1, 30);
        assertEquals(array.size(), 5);
        assertEquals(array.at(0), 20);
        assertEquals(array.at(1), 30);
        assertEquals(array.at(2), 3);
        assertEquals(array.at(3), 4);
        assertEquals(array.at(4), 5);

        array.set(2, 40);
        assertEquals(array.size(), 5);
        assertEquals(array.at(0), 20);
        assertEquals(array.at(1), 30);
        assertEquals(array.at(2), 40);
        assertEquals(array.at(3), 4);
        assertEquals(array.at(4), 5);

        array.set(3, 50);
        assertEquals(array.size(), 5);
        assertEquals(array.at(0), 20);
        assertEquals(array.at(1), 30);
        assertEquals(array.at(2), 40);
        assertEquals(array.at(3), 50);
        assertEquals(array.at(4), 5);

        array.set(4, 60);
        assertEquals(array.size(), 5);
        assertEquals(array.at(0), 20);
        assertEquals(array.at(1), 30);
        assertEquals(array.at(2), 40);
        assertEquals(array.at(3), 50);
        assertEquals(array.at(4), 60);
    }

    @Test
    void subset() {
        Sequence<Integer> sequence = new LinkedSequence<>(new Integer[]{0,1,2,3,4});

        for (int i = 0; i < sequence.size(); i++) {
            for(int j = sequence.size() - 1; j >= i; j--) {
                Sequence<Integer> subset = sequence.subset(i,j);
                assertEquals(subset.size(), j - i);
                for (int d = 0; d < j - i; d++) {
                    assertEquals(subset.at(d), d + i);
                }
            }
        }
    }


}