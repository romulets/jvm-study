package datastructures.set;

import datastructures.sequence.LinkedSequence;
import datastructures.tree.AVLTree;

import java.lang.reflect.Array;
import java.util.function.Function;

public class AVLTreeSet<T extends Comparable<T>> implements Set<T> {

    AVLTree<T> tree;
    Function<T, AVLTree<T>> treeAdapter;

    private boolean allowDuplicates;


    public AVLTreeSet(Function<T, AVLTree<T>> treeAdapter) {
        this.treeAdapter = treeAdapter;
        this.allowDuplicates = false;
    }

    public AVLTreeSet() {
        this(AVLTree::new);
    }

    /**
     * O(n)
     */
    public AVLTreeSet(T[] values) {
        this();
        if (values == null) {
            return;
        }

        for (T value : values) {
            add(value);
        }
    }

    public static <T extends Comparable<T>> AVLTreeSet<T> allowDuplicates() {
        AVLTreeSet<T> set = new AVLTreeSet<>();
        set.allowDuplicates = true;
        return set;
    }

    /**
     * O(log(n))
     */
    @Override
    public boolean contains(T value) {
        return find(value) != null;
    }

    /**
     * O(log(n))
     */
    @Override
    public T find(T value) {
        AVLTree<T> subtree = findSubTree(value);
        return subtree == null ? null : subtree.value();
    }

    /**
     * O(log(n))
     */
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

    /**
     * O(log(n)**2)
     * I believe it's log(n) to power 2 worst case
     * because we are also performing next and previous
     * inside of the search loop that is log(n)
     */
    private AVLTree<T> findToInsertInOrder(T value) {
        if (value == null) {
            return null;
        }

        AVLTree<T> inSearch = tree;
        AVLTree<T> previous = null;
        while (inSearch != null) {
            int comparison = value.compareTo(inSearch.value());
            if (comparison == 0) {
                return inSearch; //found
            }

            previous = inSearch;
            if (comparison > 0) {
                // value is bigger, to the right
                inSearch = inSearch.right();
            } else {
                // value is smaller, to the left
                inSearch = inSearch.left();
            }
        }

        // Return previous because it a value must be inserted
        // at the edge of the tree the binary search was not found.
        // e.g., for the below tree, insert h and a
        //           d
        //       b        f
        //          c   e    g
        //
        //  `a` comes on the left of `b` and `h` on the right of `g`
        //
        //  Or consider the three, insert 8 and 11
        //           9
        //       5        10
        //          7         14
        //
        //   8 comes to left of 7, and 11 comes to left of 14.
        //  If the tree becomes unbalanced, we balance it, and we have a balanced tree in orde
        return previous;
    }

    /**
     * O(log(n))
     */
    @Override
    public void add(T value) {
        if (value == null) {
            return;
        }

        if (tree == null) {
            tree = treeAdapter.apply(value);
            return;
        }

        AVLTree<T> insertion = findToInsertInOrder(value);
        if (insertion == null) {
            tree = tree.insertFirst(value);
            return;
        }

        if (value.equals(insertion.value()) && !allowDuplicates) {
            return;
        }

        if (value.compareTo(insertion.value()) > 0) {
            tree = insertion.insertNodeAfter(value);
        } else {
            tree = insertion.insertNodeBefore(value);
        }
    }

    /**
     * O(log(n))
     */
    @Override
    public T delete(T value) {
        AVLTree<T> subTree = findSubTree(value);
        if (subTree == null) {
            return null;
        }

        T refToValue = subTree.value();
        tree = subTree.deleteNode();
        return refToValue;
    }

    /**
     * O(log(n))
     */
    @Override
    public T first() {
        AVLTree<T> first = tree.first();
        return first != null ? first.value() : null;
    }

    /**
     * O(log(n))
     */
    @Override
    public T last() {
        AVLTree<T> last = tree.last();
        return last != null ? last.value() : null;
    }

    /**
     * O(log(n))
     */
    @Override
    public T findPrevious(T value) {
        AVLTree<T> subTree = findSubTree(value);
        if (subTree == null) {
            return null;
        }

        AVLTree<T> previous = subTree.previous();
        return previous != null ? previous.value() : null;
    }

    /**
     * O(log(n))
     */
    @Override
    public T findNext(T value) {
        AVLTree<T> subTree = findSubTree(value);
        if (subTree == null) {
            return null;
        }

        AVLTree<T> next = subTree.next();
        return next != null ? next.value() : null;
    }

    /**
     * O(1)
     */
    @Override
    public int size() {
        return tree != null ? tree.size() : 0;
    }

    public AVLTree<T> head() {
        return tree;
    }


    /**
     * O(n)
     * <a href="https://codestandard.net/articles/binary-tree-inorder-traversal/">Reference</a>
     */
    public T[] top(int topN) {
        if (size() == 0) {
            // we should return empty, but due to limitations in java we can't :clown:
            return null;
        }

        int itemsCount = Math.min(size(), topN);
        T[] items = (T[]) Array.newInstance(tree.value().getClass(), itemsCount);


        LinkedSequence<AVLTree<T>> stack = new LinkedSequence<>();
        AVLTree<T> current = tree;
        int i = 0;
        while ((current != null || stack.size() != 0) && i < itemsCount) {

            // add values to the end of the stack
            while (current != null) {
                stack.insertLast(current); // keep saving left
                current = current.left();
            }

            // get next inserted last
            AVLTree<T> popped = stack.deleteLast();
            items[i] = popped.value();
            i++;

            current = popped.right();
        }

        return items;
    }
}
