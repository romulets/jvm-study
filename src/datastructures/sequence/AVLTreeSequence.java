package datastructures.sequence;

import datastructures.tree.AVLTree;

public class AVLTreeSequence<T> implements Sequence<T> {

    AVLTree<T> tree;

    public AVLTreeSequence() {
    }

    /**
     * O(n)
     */
    public AVLTreeSequence(T[] values) {
        if (values == null) {
            return;
        }

        for (T value : values) {
            insertLast(value);
        }
    }

    /**
     * O(log(n))
     */
    @Override
    public void insertLast(T value) {
        if (tree == null) {
            tree = new AVLTree<>(value);
            return;
        }

        tree = tree.insertLast(value);
    }

    /**
     * O(log(n))
     */
    @Override
    public void insertFirst(T value) {
        if (tree == null) {
            tree = new AVLTree<>(value);
            return;
        }

        tree = tree.insertFirst(value);
    }

    /**
     * O(log(n))
     */
    @Override
    public void insertAt(int pos, T value) {
        if (pos < 0 || pos > size()) {
            throw new IndexOutOfBoundsException("Can't set position " + pos + " for size " + size());
        }

        if (pos == size()) {
            insertLast(value);
            return;
        }

        AVLTree<T> item = tree.transversalOrderAt(pos);
        AVLTree<T> previous = item.previous();

        if (previous == null) {
            insertFirst(value);
            return;
        }

        tree = previous.insertNodeAfter(value);
    }

    /**
     * O(log(n))
     */
    @Override
    public T deleteAt(int pos) {
        if (pos < 0 || pos >= size()) {
            throw new IndexOutOfBoundsException("Can't delete position " + pos + " for size " + size());
        }

        AVLTree<T> item = tree.transversalOrderAt(pos);
        return deleteItem(item);
    }

    /**
     * O(log(n))
     */
    private T deleteItem(AVLTree<T> item) {
        T value = item.value();
        tree = item.deleteNode();
        return value;
    }

    /**
     * O(log(n))
     */
    @Override
    public T deleteLast() {
        if (tree == null) {
            return null;
        }


        AVLTree<T> item = tree.last();
        return deleteItem(item);
    }

    /**
     * O(log(n))
     */
    @Override
    public T deleteFirst() {
        if (tree == null) {
            return null;
        }

        AVLTree<T> item = tree.first();
        return deleteItem(item);
    }

    /**
     * O(log(n))
     */
    @Override
    public T at(int i) {
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException("Can't set position " + i + " for size " + size());
        }

        return tree.transversalOrderAt(i).value();
    }

    /**
     * O(log(n))
     */
    @Override
    public T first() {
        if (tree == null) {
            return null;
        }

        return tree.first().value();
    }

    /**
     * O(log(n))
     */
    @Override
    public T last() {
        if (tree == null) {
            return null;
        }

        return tree.last().value();
    }

    /**
     * O(n)
     */
    @Override
    public Sequence<T> subset(int begin, int end) {
        AVLTreeSequence<T> copy = new AVLTreeSequence<>();
        for (int i = begin; i < end; i++) {
            copy.insertLast(at(i));
        }
        return copy;
    }

    /**
     * O(log(n))
     */
    @Override
    public void set(int i, T value) {
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException("Can't set position " + i + " for size " + size());
        }

        tree.transversalOrderAt(i).setValue(value);
    }

    /**
     * O(1)
     */
    @Override
    public int size() {
        return tree != null ? tree.size() : 0;
    }
}
