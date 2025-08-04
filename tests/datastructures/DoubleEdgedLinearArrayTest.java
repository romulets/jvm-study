package datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DoubleEdgedLinearArrayTest {

    @Test
    void constructDoubleEdgedLinearArray() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(5);

        assertEquals(array.array.length, 5);
        assertEquals(array.size(), 0);
        assertEquals(array.head(), 2);
        assertEquals(array.tail(), 2);
        assertEquals(array.capacity(), 5);
    }

    @Test
    void constructDoubleEdgedLinearArray_withArray() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(new String[]{"a", "b", "c"});

        array.insertLast("d");
        array.insertFirst("z");
        array.insertAt(2, "random");

        assertEquals(array.array.length, 12);
        assertEquals(array.capacity(), 12);
        assertEquals(array.size(), 6);
        assertEquals(array.head(), 3);
        assertEquals(array.tail(), 8);
        assertEquals(array.at(0), "z");
        assertEquals(array.at(1), "a");
        assertEquals(array.at(2), "random");
        assertEquals(array.at(3), "b");
        assertEquals(array.at(4), "c");
        assertEquals(array.at(5), "d");
    }

    @Test
    void insertFirst_singular() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(5);
        array.insertFirst("test");

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 5);
        assertEquals(array.capacity(), 5);
        assertEquals(array.size(), 1);
        assertEquals(array.head(), 2);
        assertEquals(array.tail(), 2);
        assertEquals(array.at(0), "test");
    }

    @Test
    void insertFirst_initialCapacity_1() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(1);
        array.insertFirst("test");
        array.insertFirst("test");
        array.insertFirst("test");

        assertEquals(array.array.length, 8);
        assertEquals(array.capacity(), 8);
        assertEquals(array.size(), 3);
        assertEquals(array.head(), 1);
        assertEquals(array.tail(), 3);

        assertEquals(array.deleteLast(), "test");
        assertEquals(array.deleteLast(), "test");
        assertEquals(array.deleteLast(), "test");

//        System.out.println(Arrays.toString(array.array));
    }

    @Test
    void insertLast_initialCapacity_1() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(1);
        array.insertLast("test");
        array.insertLast("test");
        array.insertLast("test");

        assertEquals(array.array.length, 4);
        assertEquals(array.capacity(), 4);
        assertEquals(array.size(), 3);
        assertEquals(array.head(), 1);
        assertEquals(array.tail(), 3);

        assertEquals(array.deleteFirst(), "test");
        assertEquals(array.deleteFirst(), "test");
        assertEquals(array.deleteFirst(), "test");

//        System.out.println(Arrays.toString(array.array));
    }

    @Test
    void insertFirst_double() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(5);
        array.insertFirst("test-1");
        array.insertFirst("test-2");

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 5);
        assertEquals(array.capacity(), 5);
        assertEquals(array.size(), 2);
        assertEquals(array.head(), 1);
        assertEquals(array.tail(), 2);
        assertEquals(array.at(0), "test-2");
        assertEquals(array.at(1), "test-1");
    }

    @Test
    void insertFirst_overflow() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(5);
        array.insertFirst("test-1");
        array.insertFirst("test-2");
        array.insertFirst("test-3");
        array.insertFirst("test-4");

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 10);
        assertEquals(array.capacity(), 10);
        assertEquals(array.size(), 4);
        assertEquals(array.head(), 1);
        assertEquals(array.tail(), 4);
        assertEquals(array.at(0), "test-4");
        assertEquals(array.at(1), "test-3");
        assertEquals(array.at(2), "test-2");
        assertEquals(array.at(3), "test-1");
    }

    @Test
    void insertFirst_quadrupleOverflow() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(2);
        for (int i = 0; i < 10; i++) {
            array.insertFirst("test-" + i);
        }

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 32);
        assertEquals(array.capacity(), 32);
        assertEquals(array.size(), 10);
        assertEquals(array.head(), 7);
        assertEquals(array.tail(), 16);
        for (int i = 0; i < 10; i++) {
            assertEquals(array.at(i), "test-" + (9 - i), "Assert pos " + i);
        }
    }

    @Test
    void insertLast_singular() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(6);
        array.insertLast("test");

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 6);
        assertEquals(array.capacity(), 6);
        assertEquals(array.size(), 1);
        assertEquals(array.head(), 3);
        assertEquals(array.tail(), 3);
        assertEquals(array.at(0), "test");
    }

    @Test
    void insertLast_double() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(6);
        array.insertLast("test-1");
        array.insertLast("test-2");

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 6);
        assertEquals(array.capacity(), 6);
        assertEquals(array.size(), 2);
        assertEquals(array.head(), 3);
        assertEquals(array.tail(), 4);
        assertEquals(array.at(0), "test-1");
        assertEquals(array.at(1), "test-2");
    }

    @Test
    void insertLast_tippleOverflow() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(2);
        for (int i = 0; i < 10; i++) {
            array.insertLast("test-" + i);
        }

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 16);
        assertEquals(array.capacity(), 16);
        assertEquals(array.size(), 10);
        assertEquals(array.head(), 4);
        assertEquals(array.tail(), 13);
        for (int i = 0; i < 10; i++) {
            assertEquals(array.at(i), "test-" + i, "Assert pos " + i);
        }
    }

    @Test
    void deleteLast_afterFirst() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(9);
        array.insertFirst("last");
        String last = array.deleteLast();

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 9);
        assertEquals(array.capacity(), 9);
        assertEquals(array.size(), 0);
        assertEquals(array.head(), 4);
        assertEquals(array.tail(), 4);
        assertEquals(last, "last");
    }

    @Test
    void deleteLast_afterTwoFirst() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(9);
        array.insertFirst("last");
        array.insertFirst("first");
        String last = array.deleteLast();

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 9);
        assertEquals(array.capacity(), 9);
        assertEquals(array.size(), 1);
        assertEquals(array.head(), 3);
        assertEquals(array.tail(), 3);
        assertEquals(last, "last");
    }

    @Test
    void deleteLast_afterFirstLast() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(9);
        array.insertFirst("first");
        array.insertLast("last");
        String last = array.deleteLast();

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 9);
        assertEquals(array.capacity(), 9);
        assertEquals(array.size(), 1);
        assertEquals(array.head(), 4);
        assertEquals(array.tail(), 4);
        assertEquals(last, "last");
    }

    @Test
    void deleteLast_growThenShrink() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(4);
        for (int i = 0; i < 16; i++) {
            array.insertFirst("t" + i);
        }

        for (int i = 0; i < 10; i++) {
            array.deleteLast();
        }

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 16);
        assertEquals(array.capacity(), 16);
        assertEquals(array.size(), 6);
        assertEquals(array.head(), 4);
        assertEquals(array.tail(), 9);

        assertEquals("t10", array.deleteLast());
        assertEquals("t11", array.deleteLast());
        assertEquals("t12", array.deleteLast());

//        System.out.println(Arrays.toString(array.array));

        assertEquals("t15", array.deleteFirst());
        assertEquals("t14", array.deleteFirst());
        assertEquals("t13", array.deleteFirst());

        assertEquals(array.array.length, 4);
        assertEquals(array.capacity(), 4);
        assertEquals(array.size(), 0);
        assertEquals(array.head(), 2);
        assertEquals(array.tail(), 2);
    }


    @Test
    void deleteFirst_growThenShrink() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(4);
        for (int i = 0; i < 32; i++) {
            array.insertLast("t" + i);
        }

        for (int i = 0; i < 28; i++) {
            array.deleteFirst();
        }

//        System.out.println(Arrays.toString(array.array));

        assertEquals(array.array.length, 8);
        assertEquals(array.capacity(), 8);
        assertEquals(array.size(), 4);
        assertEquals(array.head(), 2);
        assertEquals(array.tail(), 5);

        assertEquals("t31", array.deleteLast());
        assertEquals("t30", array.deleteLast());

//        System.out.println(Arrays.toString(array.array));

        assertEquals("t28", array.deleteFirst());
        assertEquals("t29", array.deleteFirst());

        assertEquals(array.array.length, 4);
        assertEquals(array.capacity(), 4);
        assertEquals(array.size(), 0);
        assertEquals(array.head(), 2);
        assertEquals(array.tail(), 2);
    }

    @Test
    void insertAt_beyondEnd() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(2);
        assertThrows(IndexOutOfBoundsException.class, () -> array.insertAt(1, "should fail"));
    }

    @Test
    void insertAt_beginning() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(2);
        array.insertAt(0, "first");

        assertEquals(array.array.length, 4);
        assertEquals(array.capacity(), 4);
        assertEquals(array.size(), 1);
        assertEquals(array.head(), 1);
        assertEquals(array.tail(), 1);
        assertEquals(array.at(0), "first");
    }

    @Test
    void insertAt_multiple() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(2);
        array.insertAt(0, "first");
        array.insertAt(1, "second");
        array.insertAt(2, "third");
        array.insertAt(3, "fourth");


        assertEquals(array.array.length, 8);
        assertEquals(array.capacity(), 8);
        assertEquals(array.size(), 4);
        assertEquals(array.head(), 2);
        assertEquals(array.tail(), 5);
        assertEquals(array.at(0), "first");
        assertEquals(array.at(1), "second");
        assertEquals(array.at(2), "third");
        assertEquals(array.at(3), "fourth");

//        System.out.println(Arrays.toString(array.array));
    }

    @Test
    void insertAt_middle() {
        DoubleEdgedLinearArray<String> array = new DoubleEdgedLinearArray<>(2);
        array.insertAt(0, "first");
        array.insertAt(1, "second");
        array.insertAt(2, "fourth");
        array.insertAt(3, "fifth");
        array.insertAt(2, "third");


        assertEquals(array.array.length, 8);
        assertEquals(array.capacity(), 8);
        assertEquals(array.size(), 5);
        assertEquals(array.head(), 2);
        assertEquals(array.tail(), 6);
        assertEquals(array.at(0), "first");
        assertEquals(array.at(1), "second");
        assertEquals(array.at(2), "third");
        assertEquals(array.at(3), "fourth");
        assertEquals(array.at(4), "fifth");

//        System.out.println(Arrays.toString(array.array));
    }

    @Test
    void deleteAt_invalidIndex() {
        DoubleEdgedLinearArray<Integer> array = new DoubleEdgedLinearArray<>(new Integer[]{1,2,3});
        assertThrows(IndexOutOfBoundsException.class, () -> array.deleteAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.deleteAt(3));

        DoubleEdgedLinearArray<Integer> empty = new DoubleEdgedLinearArray<>();
        assertThrows(IndexOutOfBoundsException.class, () -> empty.deleteAt(0));
        assertThrows(IndexOutOfBoundsException.class, () -> empty.deleteAt(1));
    }

    @Test
    void deleteAt_first() {
        DoubleEdgedLinearArray<Integer> array = new DoubleEdgedLinearArray<>(new Integer[]{1,2,3,4,5});

        array.deleteAt(0);
        assertEquals(array.array.length, 5);
        assertEquals(array.capacity(), 5);
        assertEquals(array.size(), 4);
        assertEquals(array.head(), 0);
        assertEquals(array.tail(), 3);

        array.deleteAt(0);
        assertEquals(array.array.length, 5);
        assertEquals(array.capacity(), 5);
        assertEquals(array.size(), 3);
        assertEquals(array.head(), 0);
        assertEquals(array.tail(), 2);

        array.deleteAt(0);
        assertEquals(array.array.length, 5);
        assertEquals(array.capacity(), 5);
        assertEquals(array.size(), 2);
        assertEquals(array.head(), 0);
        assertEquals(array.tail(), 1);

        array.deleteAt(0);
        assertEquals(array.array.length, 5);
        assertEquals(array.capacity(), 5);
        assertEquals(array.size(), 1);
        assertEquals(array.head(), 0);
        assertEquals(array.tail(), 0);

        array.deleteAt(0);
        assertEquals(array.array.length, 5);
        assertEquals(array.capacity(), 5);
        assertEquals(array.size(), 0);
        assertEquals(array.head(), 0);
        assertEquals(array.tail(), 0);
    }

    @ParameterizedTest
    @MethodSource("randomArgumentProvider")
    void randomTest(Integer[] input) {
//        System.out.println("Size: " + input.length);
        int numberOfOps = (int) (Math.random() * input.length);
//        System.out.println("Number of ops: " + numberOfOps);
        int initialCapacity = (int) (Math.random() * 20) + 1;
//        System.out.println("Initial size: " + initialCapacity);

        DoubleEdgedLinearArray<Integer> array = new DoubleEdgedLinearArray<>(initialCapacity);
        for (Integer i : input) {
            if (Math.random() % 2 == 0) {
                array.insertLast(i);
            } else {
                array.insertFirst(i);
            }
        }

        assertCapacityIsCorrect(array, initialCapacity);

        for (int i = 0; i < numberOfOps; i++) {
            if (Math.random() % 2 == 0) {
                array.deleteLast();
            } else {
                array.deleteFirst();
            }
        }

        assertEquals(array.size(), input.length - numberOfOps);

        assertCapacityIsCorrect(array, initialCapacity);

    }

    private static void assertCapacityIsCorrect(DoubleEdgedLinearArray<?> array, int initialCapacity) {
        // We are always keeping space, so how many times should the initial capacity be grown?
        double timesNeededToFit = Math.ceilDiv(array.size() * 2, initialCapacity);
        // Normalize to the growth rate, take log base 2 of the times needed to fit
        // That returns "roughly" the times a growth had to happen.
        // Why "roughly"? At times the growth is bigger than it's actually needed.
        // This could "and probably" is a bug. Likely on how am I repositioning heads
        // But the cost of understanding it is high right. Therefore, I expect the capacity
        // to be either the correct one or one above. Nothing more, nothing less.
        double expectedExpo = Math.ceil(Math.log(timesNeededToFit) / Math.log(2));
        int expectedCapacity1 = (int) (Math.pow(2, expectedExpo) * initialCapacity);
        int expectedCapacity2 = (int) (Math.pow(2, expectedExpo - 1) * initialCapacity);

        assertTrue(array.capacity() == expectedCapacity1 || array.capacity() == expectedCapacity2, "Array capacity should be either " + expectedCapacity1 + " or " + expectedCapacity2 + " but got " + array.capacity());
    }


    static Stream<Arguments> randomArgumentProvider() {
        int times = 100;
        Arguments[] arguments = new Arguments[times];
        for (int i = 0; i < times; i++) {
            arguments[i] = Arguments.of((Object) randomSizedArray());
        }

        return Arrays.stream(arguments);
    }

    static Integer[] randomSizedArray() {
        int size = (int) (Math.random() * 1000) + 1;
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

}