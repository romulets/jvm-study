package datastructures.sequence;

import datastructures.tree.AVLTree;

public class BinaryTreeSequence<T> implements Sequence<T> {

    AVLTree<T> tree;

    public BinaryTreeSequence() {
    }

    public BinaryTreeSequence(T[] values) {
        if (values == null) {
            return;
        }

        for (T value : values) {
            insertLast(value);
        }
    }

    @Override
    public void insertLast(T value) {
        if (tree == null) {
            tree = new AVLTree<>(value);
            return;
        }

        tree = tree.insertLast(value);
    }

    @Override
    public void insertFirst(T value) {
        if (tree == null) {
            tree = new AVLTree<>(value);
            return;
        }

        tree = tree.insertFirst(value);
    }

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

    @Override
    public T deleteAt(int pos) {
        if (pos < 0 || pos >= size()) {
            throw new IndexOutOfBoundsException("Can't delete position " + pos + " for size " + size());
        }

        AVLTree<T> item = tree.transversalOrderAt(pos);
        return deleteItem(item);
    }

    private T deleteItem(AVLTree<T> item) {
        T value = item.value();
        tree = item.deleteNode();
        return value;
    }

    @Override
    public T deleteLast() {
        if (tree == null) {
            return null;
        }


        AVLTree<T> item = tree.last();
        return deleteItem(item);
    }

    @Override
    public T deleteFirst() {
        if (tree == null) {
            return null;
        }

        AVLTree<T> item = tree.first();
        return deleteItem(item);
    }

    @Override
    public T at(int i) {
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException("Can't set position " + i + " for size " + size());
        }

        return tree.transversalOrderAt(i).value();
    }

    @Override
    public T first() {
        if (tree == null) {
            return null;
        }

        return tree.first().value();
    }

    @Override
    public T last() {
        if (tree == null) {
            return null;
        }

        return tree.last().value();
    }

    @Override
    public Sequence<T> subset(int begin, int end) {
        BinaryTreeSequence<T> copy = new BinaryTreeSequence<>();
        for (int i = begin; i < end; i++) {
            copy.insertLast(at(i));
        }
        return copy;
    }

    @Override
    public void set(int i, T value) {
        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException("Can't set position " + i + " for size " + size());
        }

        tree.transversalOrderAt(i).setValue(value);
    }

    @Override
    public int size() {
        return tree != null ? tree.size() : 0;
    }
}
