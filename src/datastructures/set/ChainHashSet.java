package datastructures.set;

import datastructures.array.LinkedArray;

import java.util.Objects;

public class ChainHashSet<T> implements Set<T> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_RATIO = 2;
    private static final int MAX_CHAIN_SIZE = 10;
    private int size;
    private Object[] hashTable;

    /**
     * O(1)
     */
    public ChainHashSet(int capacity) {
        hashTable = new Object[capacity];
        size = 0;
    }

    /**
     * O(1)
     */
    public ChainHashSet() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * O(n)
     */
    public ChainHashSet(T[] values) {
        if (values == null || values.length < DEFAULT_CAPACITY) {
            hashTable = new Object[DEFAULT_CAPACITY];
        }

        if (values != null) {
            for (T value : values) {
                add(value);
            }
        }
    }

    /**
     * O(n) considering that chain doesn't grow bigger than {ChainHashSet.MAX_CHAIN_SIZE}
     */
    @Override
    public boolean contains(T value) {
        int pos = findHashPosition(value);
        LinkedArray<T> chain = findChain(pos);

        for (int i = 0; i < chain.size(); i++) {
            if (Objects.equals(value, chain.at(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Amortized O(1)
     */
    @Override
    public void add(T value) {
        int pos = findHashPosition(value);
        LinkedArray<T> chain = findChain(pos);

        if (chain.size() == MAX_CHAIN_SIZE) {
            throw new IndexOutOfBoundsException("chain size = 10, needs to be fixed");
        }

        for (int i = 0; i < chain.size(); i++) {
            if (Objects.equals(value, chain.at(i))) {
                // already exists
                return;
            }
        }

        chain.insertLast(value);
        hashTable[pos] = chain;
        size++;
    }

    /**
     * O(1) (shrink not implemented)
     */
    @Override
    public void delete(T value) {
        int pos = findHashPosition(value);
        LinkedArray<T> chain = findChain(pos);

        if (chain.size() == 0) {
            return;
        }

        for (int i = 0; i < chain.size(); i++) {
            if (Objects.equals(value, chain.at(i))) {
                chain.deleteAt(i);
                size--;
                return;
            }
        }
    }

    /**
     * O(n)
     */
    @Override
    public T first() {
        if (size == 0) {
            return null;
        }

        for (int i = 0; i < hashTable.length; i++) {
            T value = findChain(i).first();
            if (value != null) {
                return value;
            }
        }

        return null;
    }

    /**
     * O(n)
     */
    @Override
    public T last() {
        if (size == 0) {
            return null;
        }

        for (int i = hashTable.length - 1; i >= 0; i--) {
            T value = findChain(i).last();
            if (value != null) {
                return value;
            }
        }

        return null;
    }

    @Override
    public T findPrevious(T value) {
        return null;
    }

    /**
     * O(n)
     */
    @Override
    public T findNext(T value) {
        int chainPos = findHashPosition(value);
        LinkedArray<T> chain = findChain(chainPos);

        boolean found = false;
        for (int i = 0; i < chain.size(); i++) {
            if (Objects.equals(value, chain.at(i))) {
                found = true;
                continue;
            }

            // if it was found in the previous run, just return it;
            if (found) {
                return chain.at(i);
            }
        }

        // If it was not found null
        if (!found) {
            return null;
        }

        // if it was found but not returned, it's in some next chain;
        for(int chainIdx = chainPos + 1; chainIdx < hashTable.length; chainIdx++) {
            LinkedArray<T> nextChain = findChain(chainIdx);
            if (nextChain.size() > 0) {
                return nextChain.first();
            }
        }

        // This will happen in case there is no next
        return null;
     }

    @Override
    public int size() {
        return size;
    }

    private LinkedArray<T> findChain(int pos) {
        LinkedArray<T> chain;
        if (hashTable[pos] == null) {
            chain = new LinkedArray<T>();
        } else {
            chain = (LinkedArray<T>) hashTable[pos];
        }

        return chain;
    }

    /**
     * O(n)
     * I know this is dump and very collision prone. I'm doing for simplicity
     */
    private int findHashPosition(T value) {
        return Objects.hash(value) % hashTable.length;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(size * 2 + 2);

        builder.append("<");
        boolean appendComma = false;
        for (int i = 0; i < hashTable.length; i++) {
            if (findChain(i).size() == 0) {
                continue;
            }

            if (appendComma) {
                builder.append(",");
            }

            builder.append(findChain(i));
            appendComma = true;
        }

        builder.append(">");

        return builder.toString();
    }
}
