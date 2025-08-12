package datastructures.set;

import datastructures.tree.AVLTree;

public class BinaryTreeSet<T extends Comparable<T>> implements Set<T> {

    AVLTree<T> tree;


    public BinaryTreeSet() {
    }

    public BinaryTreeSet(T[] values) {
        if (values == null) {
            return;
        }

        for (T value : values) {
            add(value);
        }
    }

    @Override
    public boolean contains(T value) {
        return find(value) != null;
    }

    @Override
    public T find(T value) {
        AVLTree<T> subtree = findSubTree(value);
        return subtree == null ? null : subtree.value();
    }

    private AVLTree<T> findSubTree(T value) {
        if (value == null) {
            return null;
        }

        AVLTree<T> inSearch = tree;
        while (inSearch != null) {
            int comparison = value.compareTo(inSearch.value());
            if (comparison == 0) {
                return inSearch; //found
            }

            if (comparison > 0) {
                // value is bigger, to the right
                inSearch = inSearch.right();
            } else {
                // value is smaller, to the left
                inSearch = inSearch.left();
            }
        }

        return null;
    }

    private AVLTree<T> findToInsertInOrder(T value) {
        if (value == null) {
            return null;
        }

        AVLTree<T> first = tree.first();
        int compareFirst = value.compareTo(first.value());
        if (compareFirst == 0) {
            return first;
        } else if (compareFirst < 0) {
            // No previous, null
            return null;
        }

        AVLTree<T> last = tree.last();
        if (value.compareTo(last.value()) >= 0) {
            return last;
        }

        AVLTree<T> inSearch = tree;
        while (inSearch != null) {
            int comparison = value.compareTo(inSearch.value());
            if (comparison == 0) {
                return inSearch; // return exact match
            }

            AVLTree<T> next = inSearch.next();
            AVLTree<T> previous = inSearch.previous();

            if (comparison > 0) {
                // current value is bigger than inSearch

                // If value is larger than InSearch and smaller than next
                if (value.compareTo(next.value()) <= 0) {
                    return inSearch;
                }

                // value is bigger, to the right
                inSearch = inSearch.right();
            } else {
                // current value is smaller than inSearch

                // If value is smaller than InSearch and larger than previous
                if (value.compareTo(previous.value()) >= 0) {
                    // insert after previous
                    return previous;
                }

                // value is smaller, to the left
                inSearch = inSearch.left();
            }
        }

        return null;
    }

    @Override
    public void add(T value) {
        if (value == null) {
            return;
        }

        if (tree == null) {
            tree = new AVLTree<>(value);
            return;
        }

        AVLTree<T> insertion = findToInsertInOrder(value);
        if (insertion == null) {
            tree = tree.insertFirst(value);
            return;
        }
        if (value.equals(insertion.value())) {
            return;
        }

        tree = insertion.insertNodeAfter(value);
    }

    @Override
    public void delete(T value) {
        AVLTree<T> subTree = findSubTree(value);
        if (subTree == null) {
            return;
        }

        tree = subTree.deleteNode();
    }

    @Override
    public T first() {
        AVLTree<T> first = tree.first();
        return first != null ? first.value() : null;
    }

    @Override
    public T last() {
        AVLTree<T> last = tree.last();
        return last != null ? last.value() : null;
    }

    @Override
    public T findPrevious(T value) {
        AVLTree<T> subTree = findSubTree(value);
        if (subTree == null) {
            return null;
        }

        AVLTree<T> previous = subTree.previous();
        return previous != null ? previous.value() : null;
    }

    @Override
    public T findNext(T value) {
        AVLTree<T> subTree = findSubTree(value);
        if (subTree == null) {
            return null;
        }

        AVLTree<T> next = subTree.next();
        return next != null ? next.value() : null;
    }

    @Override
    public int size() {
        return tree != null ? tree.size() : 0;
    }
}
