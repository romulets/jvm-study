package datastructures.tree;

/**
 * Keeps height balanced
 * Does not guarantee order
 *
 * @param <T>
 */
public class AVLTree<T> {
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

    public AVLTree<T> insertNodeAfter(T value) {
        if (this.right == null) {
            this.right = new AVLTree<>(value);
        } else {
            AVLTree<T> tmp = this.right;
            this.right = new AVLTree<>(value);
            this.right.right = tmp;
            this.right.right.parent = this.right;
            this.right.updateComputedProperties();
        }

        this.right.parent = this;

        // return parent
        AVLTree<T> parent = balanceHeight();
        AVLTree<T> previousParent = parent;
        while (parent != null) {
            // doing for each parent to make sure we are balanced after insertion
            parent = parent.balanceHeight();
            parent.updateComputedProperties();
            previousParent = parent;
            parent = parent.parent;
        }

        return previousParent;
    }

    private void updateComputedProperties() {
        this.size = size(left) + size(right) + 1;
        this.height = Math.max(height(left), height(right)) + 1;
    }

    /**
     * O(log(n))
     *
     * @return new root
     */
    private AVLTree<T> balanceHeight() {
        int heightSkew = heightSkew();
        if (Math.abs(heightSkew) <= 1) { // within good boundaries
            return this;
        }

        AVLTree<T> newRoot;
        if (heightSkew > 1) {
            // right is taller than left
            if (right.heightSkew() < 0) {
                // skew of right is -1
                right.rotateRight();
            }
            newRoot = rotateLeft();
        } else {
            // left is taller than right
            if (left.heightSkew() > 0) {
                // skew of left is 1
                left.rotateLeft();
            }
            newRoot = rotateRight();
        }

        // update all parents
        AVLTree<T> parent = newRoot.parent;
        while (parent != null) {
            parent.updateComputedProperties();
            parent = parent.parent;
        }

        return newRoot;
    }

    private int heightSkew() {
        return height(right) - height(left);
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
            if (unchangedParent.right == this) {
                unchangedParent.right = newRoot;
            } else {
                unchangedParent.left = newRoot;
            }
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
        AVLTree<T> rotatedChild = newRoot.left; // null

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
            if (unchangedParent.right == this) {
                unchangedParent.right = newRoot;
            } else {
                unchangedParent.left = newRoot;
            }
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
    public AVLTree<T> deleteNode() {
        if (isLeaf()) {
            return deleteLeaf();
        }

        AVLTree<T> toBeDeleted = this;
        while (!toBeDeleted.isLeaf()) {
            // find swap
            AVLTree<T> toBeSwapped = toBeDeleted.next();
            if (toBeSwapped == null) {
                toBeSwapped = toBeDeleted.previous();
            }

            // Swap values
            T tmpValue = toBeSwapped.value();
            toBeSwapped.value = toBeDeleted.value;
            toBeDeleted.value = tmpValue;
            // go next!
            toBeDeleted = toBeSwapped;
        }

        return toBeDeleted.deleteLeaf();
    }

    private AVLTree<T> deleteLeaf() {
        if (parent == null) {
            return null;
        }

        if (this.parent.left == this) {
            this.parent.left = null;
        } else if (this.parent.right == this) {
            this.parent.right = null;
        }

        // walk up parents updating computed properties
        AVLTree<T> parent = this.parent;
        AVLTree<T> lastParent = this.parent;
        while (parent != null) {
            parent.updateComputedProperties();
            parent = parent.balanceHeight();
            lastParent = parent;
            parent = parent.parent;
        }

        return lastParent;
    }

    /**
     * O(1)
     */
    boolean isLeaf() {
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
    public AVLTree<T> transversalOrderAt(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IllegalArgumentException("Can't access node " + pos + " in a tree of size pos");
        }

        AVLTree<T> atPos = this.first();
        for (int i = 0; i < pos; i++) {
            atPos = atPos.next();
        }

        return atPos;
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

    public AVLTree<T> left() {
        return left;
    }

    public AVLTree<T> right() {
        return right;
    }
}
