package datastructures.array;

public class LinkedArray<T> implements Array<T> {

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * O(1)
     */
    public LinkedArray() {
    }

    /**
     * O(n)
     */
    public LinkedArray(T[] initialValues) {
        if (initialValues == null) {
            throw new IllegalArgumentException("Argument can't be null");
        }

        for(T v : initialValues) {
            insertLast(v);
        }
    }

    /**
     * O(n)
     */
    @Override
    public void insertLast(T value) {
        Node<T> tmp = tail;
        tail = new Node<>(value);

        if (tmp != null) {
            tmp.next = tail;
            tail.previous = tmp;
        }

        if (size == 0) {
            head = tail;
        }
        size++;
    }

    /**
     * O(n)
     */
    @Override
    public void insertFirst(T value) {
        Node<T> tmp = head;
        head = new Node<>(value);

        if (tmp != null) {
            tmp.previous = head;
            head.next = tmp;
        }

        if (size == 0) {
            tail = head;
        }

        size++;
    }

    /**
     * O(n)
     */
    @Override
    public void insertAt(int pos, T value) {
        if (pos < 0 || pos > size()) {
            throw new IndexOutOfBoundsException("Can't insert pos " + pos + " in a array of " + size);
        }

        if (pos == 0) {
            insertFirst(value);
            return;
        }

        if (pos == size()) {
            insertLast(value);
            return;
        }

        //find position
        Node<T> current = head;
        Node<T> previous = null;
        for (int i = 0; i < pos; i++) {
            previous = current;
            current = current.next;
        }

        Node<T> newNode = new Node<>(value);
        newNode.previous = previous;
        newNode.next = current;

        current.previous = newNode;
        previous.next = newNode;

        size++;
    }

    /**
     * O(n)
     */
    @Override
    public T deleteAt(int pos) {
        if (pos < 0 || pos >= size()) {
            throw new IndexOutOfBoundsException("Can't delete pos " + pos + " in a array of " + size);
        }

        if (pos == 0) {
            return deleteFirst();
        }

        if (pos == size() - 1) {
            return deleteLast();
        }

        //find position
        Node<T> current = head;
        Node<T> previous = null;
        for (int i = 0; i < pos; i++) {
            previous = current;
            current = current.next;
        }

        Node<T> toReturn = current;

        previous.next = current.next;
        current.next = previous;

        size--;

        return current.value;
    }

    /**
     * O(1)
     */
    @Override
    public T deleteLast() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Can't delete last in a array of " + size);
        }

        Node<T> toReturn = tail;

        if(size == 1) {
            tail = null;
            head = null;
        } else {
            tail = tail.previous;
            tail.next = null;
        }

        size--;
        return toReturn.value;
    }

    /**
     * O(1)
     */
    @Override
    public T deleteFirst() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Can't delete last in a array of " + size);
        }

        Node<T> toReturn = head;

        if(size == 1) {
            tail = null;
            head = null;
        } else {
            head = head.next;
            if (head.next != null) {
                head.next.previous = head;
            }
        }

        size--;
        return toReturn.value;
    }

    /**
     * O(n)
     */
    @Override
    public T at(int i) {
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException("Can't access pos " + i + " in a array of " + size);
        }

        Node<T> current = head;
        for (int j = 0; j < i; j++) {
            current = current.next;
        }
        return current.value;
    }

    /**
     * O(1)
     */
    @Override
    public T first() {
        return head.value;
    }

    /**
     * O(1)
     */
    @Override
    public T last() {
        return tail.value;
    }

    /**
     * O(n)
     */
    @Override
    public Array<T> subset(int begin, int end) {
        Node<T> current = head;
        for (int i = 0; i < begin; i++) {
            current = current.next;
        }

        Array<T> subset = new LinkedArray<>();
        for (int i = 0; i < end - begin; i++) {
            subset.insertLast(current.value);
            current = current.next;
        }

        return subset;
    }

    /**
     * O(n)
     */
    @Override
    public void set(int i, T value) {
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException("Can't set pos " + i + " in a array of " + size);
        }

        Node<T> current = head;
        for (int j = 0; j < i; j++) {
            current = current.next;
        }

        current.value = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(size * 2 + 2);

        builder.append("[");
        Node<T> current = head;
        while (current != null) {
            builder.append(current.value);
            current = current.next;
            if (current != null) {
                builder.append(",");
            }
        }

        builder.append("]");

        return builder.toString();
    }
}
