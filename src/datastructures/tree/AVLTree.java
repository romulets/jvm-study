package datastructures.tree;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Keeps height balanced
 * Does not guarantee order
 *
 * @param <T>
 */
class AVLTree<T> {
    private T value;
    private AVLTree<T> parent;
    private AVLTree<T> left;
    private AVLTree<T> right;
    private int size;
    private int height;

    public AVLTree(T value) {
        this.value = value;
        size = 1;
        height = 1;
    }

    /**
     * O(log(n))
     *
     * @return new root
     */
    public AVLTree<T> insertLast(T value) {
        if (this.right != null) { // if we have a left, add to its node
            this.right.insertLast(value);
        } else { // if we don't have a left, add a new left
            this.right = new AVLTree<>(value);
            this.right.parent = this;
        }

        updateComputedProperties();
        return balanceHeight();
    }


    /**
     * O(log(n))
     *
     * @return new root
     */
    public AVLTree<T> insertFirst(T value) {
        if (this.left != null) { // if we have a left, add to its node
            this.left.insertFirst(value);
        } else { // if we don't have a left, add a new left
            this.left = new AVLTree<>(value);
            this.left.parent = this;
        }

        updateComputedProperties();
        return balanceHeight();
    }

    private void updateComputedProperties() {
        this.size = size(left) + size(right) + 1;
        this.height = Math.max(height(left), height(right)) + 1;
    }

    /**
     * O(1)
     *
     * @return new root
     */
    private AVLTree<T> balanceHeight() {
        int heightSkew = getHeightSkew();
        if (Math.abs(heightSkew) <= 1) { // within good boundaries
            return this;
        }
        System.out.println("----------- BEFORE ------------");
        System.out.println(this.treeDiagram());

        AVLTree<T> newRoot;
        if (heightSkew > 1) {
            newRoot = rotateRight();
        } else {
            newRoot = rotateLeft();
        }

        System.out.println("----------- AFTER ------------");
        System.out.println(newRoot.treeDiagram());

        return newRoot;
    }

    private int getHeightSkew() {
        return height(left) - height(right);
    }

    /**
     * O(1)
     *
     * @return new root
     */
    private AVLTree<T> rotateRight() {
        AVLTree<T> unchangedParent = this.parent;
        AVLTree<T> newRoot = this.left;
        AVLTree<T> newRightChild = this;
        AVLTree<T> rotatedChild = newRoot.right;

        // becomes parent
        newRoot.right = newRightChild;
        newRoot.parent = unchangedParent;

        // becomes child
        newRightChild.parent = newRoot;
        newRightChild.left = rotatedChild;

        if (rotatedChild != null) {
            // points to new parent
            rotatedChild.parent = newRightChild;
        }

        if (unchangedParent != null) {
            // points to the newRoot
            unchangedParent.left = newRoot;
        }

        newRightChild.updateComputedProperties();
        newRoot.updateComputedProperties();

        return newRoot;
    }


    /**
     * O(1)
     *
     * @return new root
     */
    private AVLTree<T> rotateLeft() {
        AVLTree<T> unchangedParent = this.parent;
        AVLTree<T> newRoot = this.right;
        AVLTree<T> newLeftChild = this;
        AVLTree<T> rotatedChild = newRoot.left;

        // becomes parent
        newRoot.left = newLeftChild;
        newRoot.parent = unchangedParent;

        // becomes child
        newLeftChild.parent = newRoot;
        newLeftChild.right = rotatedChild;

        if (rotatedChild != null) {
            // points to new parent
            rotatedChild.parent = newLeftChild;
        }

        if (unchangedParent != null) {
            // points to the newRoot
            unchangedParent.right = newRoot;
        }


        newLeftChild.updateComputedProperties();
        newRoot.updateComputedProperties();

        return newRoot;
    }


    /**
     * O(log(n))
     *
     * @return new root
     */
    public AVLTree<T> delete() {
        if (isLeaf() && parent == null) {
            return null; // no new parent, it's completely null
        }

        if (isLeaf()) {
            if (this.parent.left == this) {
                this.parent.left = null;
            } else if (this.parent.right == this) {
                this.parent.right = null;
            }

            // walk up parents updating computed properties
            AVLTree<T> parent = this.parent;
            while (parent.parent != null) {
                parent.updateComputedProperties();
                parent = parent.balanceHeight();
                parent = parent.parent;
            }
            parent.updateComputedProperties();
            return parent;
        }

        return this;
    }

    /**
     * O(1)
     */
    private boolean isLeaf() {
        return size == 1;
    }

    /**
     * O(1)
     */
    private int size(AVLTree<T> tree) {
        return tree == null ? 0 : tree.size;
    }

    /**
     * O(1)
     */
    private int height(AVLTree<T> tree) {
        return tree == null ? 0 : tree.height;
    }

    /**
     * O(1)
     */
    public int size() {
        return size;
    }

    /**
     * O(1)
     */
    public int height() {
        return height;
    }

    /**
     * O(1)
     */
    public T value() {
        return value;
    }

    /**
     * O(n)
     */
    public String transversalOrder() {
        // just a buffer size since we don't know the string representation of values
        StringBuilder builder = new StringBuilder(size * 16);

        if (this.left != null) {
            builder.append(this.left.transversalOrder());
            builder.append(",");
        }

        builder.append(this.value);

        if (this.right != null) {
            builder.append(",");
            builder.append(this.right.transversalOrder());
        }

        return builder.toString();
    }

    /**
     * O(n)
     */
    public AVLTree<T> first() {
        AVLTree<T> next = this;
        while (next.left != null) {
            next = next.left;
        }

        return next;
    }

    /**
     * O(n)
     */
    public AVLTree<T> last() {
        AVLTree<T> previous = this;
        while (previous.right != null) {
            previous = previous.right;
        }

        return previous;
    }

    /**
     * O(n)
     */
    public AVLTree<T> next() {
        /*
        EXAMPLE TO REASON ABOUT

                (d)
            (b)     (f)
          (a) (c) (e) (g)
         */

        if (parent == null) {
            if (this.right == null) {
                return null;
            }
            return right.first();
        }


        // after b is c
        // after d is e
        // after f is g
        if (this.right != null) {
            return this.right.first();
        }

        // after a, is b
        // after e, is f
        if (parent.left == this) {
            return parent;
        }

        // after c is d
        // after g is NULL
        AVLTree<T> parentLadder = parent;
        AVLTree<T> current = this;
        // While the current branch is the right side of parent, walk up!
        while (parentLadder != null && parentLadder.right == current) {
            current = parentLadder;
            parentLadder = current.parent;
        }

        return parentLadder;
    }

    /**
     * O(n)
     */
    public AVLTree<T> previous() {
        /*
        EXAMPLE TO REASON ABOUT

                (d)
            (b)     (f)
          (a) (c) (e) (g)
        */

        if (parent == null) {
            // if head has no left, we are finished
            if (this.left == null) {
                return null;
            }
            // before d is c
            return left.last();
        }

        // before b is a
        // before f is e
        if (this.left != null) {
            return this.left.last();
        }

        // before g is f
        // before c is b
        if (parent.right == this) {
            return parent;
        }

        // before e is d
        // before d is nothing
        AVLTree<T> parentLadder = parent;
        AVLTree<T> current = this;
        // While the current branch is the left side of parent, walk up!
        while (parentLadder != null && parentLadder.left == current) {
            current = parentLadder;
            parentLadder = current.parent;
        }

        return parentLadder;
    }


    /**
     * O(n)
     */
    @Override
    public String toString() {
        return "<" + this.value + " (s:" + size + " h: " + height + ")>";
    }

    /**
     * O(n) not optimized at all, and ugly. Just aids development
     */
    public String treeDiagram() {
        String thisDiagram = toString();
        if (isLeaf()) {
            return thisDiagram; // early return if there are no children
        }

        String leftDiagram = "";
        if (this.left != null) {
            leftDiagram = this.left.treeDiagram();
            leftDiagram = Arrays.stream(leftDiagram.split("\\n"))
                    .map(l -> {
                        if (l.charAt(0) == '<') {
                            return "├─ l" + l;
                        }
                        return "├─ " + l;
                    })
                    .collect(Collectors.joining("\n"));
        }

        String rightDiagram = "";
        if (this.right != null) {
            rightDiagram = this.right.treeDiagram();
            rightDiagram = Arrays.stream(rightDiagram.split("\\n"))
                    .map(l -> {
                        if (l.charAt(0) == '<') {
                            return "├─ r" + l;
                        }
                        return "├─ " + l;
                    })
                    .collect(Collectors.joining("\n"));
        }



        StringBuilder builder = new StringBuilder(
                this.size() + (leftDiagram.length()) + (rightDiagram.length())
        );

        builder.append(thisDiagram);
        builder.append("\n");
        if (this.left != null) {
            builder.append(leftDiagram);
            builder.append("\n");
        }
        if (this.right != null) {
            builder.append(rightDiagram);
            builder.append("\n");
        }

        return builder.toString();
    }
}
