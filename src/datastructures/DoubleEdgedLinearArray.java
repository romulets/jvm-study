package datastructures;

import java.util.Arrays;

public class DoubleEdgedLinearArray<T> {

    private static final int GROWTH_RATIO = 2;

    Object[] array;
    private int capacity;
    private final int initialCapacity;
    private int size;
    private int head;
    private int tail;


    /**
     * O(n)
     *
     * @param initialCapacity
     */
    public DoubleEdgedLinearArray(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalStateException("Capacity must be larger than 1");
        }

        this.array = new Object[initialCapacity];
        this.size = 0;
        this.capacity = initialCapacity;
        this.initialCapacity = initialCapacity;
        this.head = capacity / GROWTH_RATIO;
        this.tail = head;
    }

    /**
     * O(n)
     */
    public DoubleEdgedLinearArray() {
        this(GROWTH_RATIO * 8);
    }

    /**
     * O(n)
     *
     * @param input
     */
    public DoubleEdgedLinearArray(T[] input) {
        if (input == null || input.length == 0) {
            throw new IllegalStateException("Invalid empty or null input array");
        }

        this.array = Arrays.copyOf(input, input.length);
        this.size = input.length;
        this.capacity = input.length;
        this.initialCapacity = input.length;
        this.head = 0;
        this.tail = input.length - 1;
    }

    /**
     * Amortized O(1) since in most operations is O(1)
     *
     * @param value
     */
    public void insertLast(T value) {
        if (size == 0) {
            array[tail] = value;
            size++;
            return;
        }

        // Move tail to insert it
        int nextTail = tail + 1;

        // Out of space
        if (size + head + 1 > capacity) {
            growCapacity();
            nextTail = tail + 1;
        }

        array[nextTail] = value;
        tail = nextTail;

        size++;
    }

    /**
     * Amortized O(1) since in most operations is O(1)
     *
     * @param value
     */
    public void insertFirst(T value) {
        if (size == 0) {
            array[head] = value;
            size++;
            return;
        }

        // Move head to insert it
        int nextHead = head - 1;

        // Out of space
        if (nextHead < 0) {
            growCapacity();
            // After growth, head points to the current head
            // Move head again to insert it;
            nextHead = head - 1;

            // EDGE CASE - if the initial capacity is 1 we can still grow to two and have head = 0
            // I chose to solve by calling it again.
            // Another solution would have a requirement for min capacity 2
            if (nextHead < 0) {
                growCapacity();
                nextHead = head - 1;

            }
        }

        array[nextHead] = value;
        head = nextHead;

        size++;
    }

    /**
     * O(n)
     * Adds a new value in the middle.
     * It doesn't add values beyond the end
     *
     * @param pos
     * @param value
     */
    public void insertAt(int pos, T value) {
        if (pos > size) {
            throw new IndexOutOfBoundsException("Can't set position " + pos + " for size " + size);
        }

        if (size + head + 1 >= capacity) {
            growCapacity();
        }

        // Copy values forward
        for (int i = size; i > pos; i--) {
            array[head + i] = array[head + i - 1];
        }

        // insert
        array[head + pos] = value;
        size++;
        if (size > 1) {
            tail++;
        }
    }

    /**
     * Amortized O(1) since in most operations is O(1)
     */
    public T deleteLast() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("There are no elements to remove");
        }

        T value = at(size - 1);
        cleanPos(size - 1);
        size--;

        if (size == 0) {
            head = tail;
        } else {
            tail--;
        }

        shrinkIfNeeded();

        return value;
    }

    /**
     * Amortized O(1) since in most operations is O(1)
     */
    public T deleteFirst() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("There are no elements to remove");
        }

        T value = at(0);
        cleanPos(0);
        size--;

        if (size == 0) {
            tail = head;
        } else {
            head++;
        }

        shrinkIfNeeded();

        return value;
    }

    /**
     * O(1) access. Leave array out of bounds handling to java
     *
     * @param i
     * @return
     */
    public T at(int i) {
        if (i > size - 1 || i < 0) {
            throw new IndexOutOfBoundsException("Access " + i + " in an array of size " + size);
        }
        //noinspection unchecked
        return (T) array[head + i];
    }

    /**
     * O(1)
     *
     * @return
     */
    public T first() {
        return at(0);
    }

    /**
     * O(1)
     *
     * @return
     */
    public T last() {
        return at(size - 1);
    }

    /**
     * O(1)
     *
     * @param i
     */
    private void cleanPos(int i) {
        array[head + i] = null;
    }

    /**
     * O(n)
     * Using lists due to a limitation of creating generic lists
     *
     * @param begin
     * @param end
     * @return copy of sub array
     */
    public DoubleEdgedLinearArray<T> subset(int begin, int end) {
        DoubleEdgedLinearArray<T> copy = new DoubleEdgedLinearArray<T>(end - begin);
        for (int i = begin; i < end; i++) {
            copy.insertLast(at(i));
        }
        return copy;
    }

    /**
     * O(1).
     * It just overrides. It doesn't increase size
     *
     * @param i
     * @param value
     */
    public void set(int i, T value) {
        if (i > size - 1 || i < 0) {
            throw new IndexOutOfBoundsException("Access " + i + " in an array of size " + size);
        }
        array[head + i] = value;
    }

    /**
     * O(capacity * GROWTH_RATIO) - O(n) growth for array allocation + copy of values
     */
    private void growCapacity() {
        // resize
        int newCapacity = capacity * GROWTH_RATIO;

        resize(new Object[newCapacity]);
    }

    /**
     * if size is less than a 1/4 of current capacity shrink to half of it (or initial capacity)
     * O(n) shrink for array allocation + copy of values
     */
    private void shrinkIfNeeded() {
        // initial capacity or the ratio is not 1/4
        if (size == 0 || capacity == initialCapacity || capacity / size < 4) {
            return;
        }

        // resize
        int newCapacity = capacity / GROWTH_RATIO;
        assert newCapacity >= initialCapacity; // using assert here to fail if it happens, should not

        // find new head (leave 1/4 of it)
        resize(new Object[newCapacity]);
    }

    /**
     * O(n) for the copy
     *
     * @param newArray
     */
    private void resize(Object[] newArray) {
        // find new head (leave 1/4 of it)
        int newHead = newArray.length / GROWTH_RATIO / GROWTH_RATIO;

        for (int i = 0; i < size; i++) {
            newArray[newHead + i] = at(i);
        }

        head = newHead;
        tail = newHead + size - 1;
        array = newArray;
        capacity = newArray.length;
    }

    public int size() {
        return size;
    }

    public int head() {
        return head;
    }

    public int tail() {
        return tail;
    }

    public int capacity() {
        return capacity;
    }

    /**
     * O(n)
     *
     * @return
     */
    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(array, head, tail + 1));
    }
}
