package datastructures.set;

import datastructures.sequence.LinkedSequence;

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
     * O(1) considering that chain doesn't grow bigger than {ChainHashSet.MAX_CHAIN_SIZE}
     */
    @Override
    public boolean contains(T value) {
      return find(value) != null;
    }

    /**
     * I know this seems dumb, but it's super useful
     * for HashMap. We search by hash, but some metadata
     * not present in the hash might be the result (KeyValue)
     * <p>
     * O(1) considering that chain doesn't grow bigger than {ChainHashSet.MAX_CHAIN_SIZE}
     */
    @Override
    public T find(T value) {
        int pos = findHashPosition(value, hashTable);  // O(1)
        LinkedSequence<T> chain = findChain(pos, hashTable);  // O(1)

        for (int i = 0; i < chain.size(); i++) {  // O(10)
            T foundValue = chain.at(i);
            if (Objects.equals(value, foundValue)) {
                return foundValue;
            }
        }

        return null;
    }

    /**
     * Amortized O(1)
     */
    @Override
    public void add(T value) {
        if (add(hashTable, value, true)) {
            size++;
        }
    }

    /**
     * Amortized O(1)
     * "Stateless" add to reuse once growing
     */
    private boolean add(Object[] hashTable, T value, boolean allowGrowth) {
        int pos = findHashPosition(value, hashTable);
        LinkedSequence<T> chain = findChain(pos, hashTable);

        for (int i = 0; i < chain.size(); i++) {
            if (Objects.equals(value, chain.at(i))) {
                // already exists
                return false;
            }
        }

        if (chain.size() == MAX_CHAIN_SIZE) {
            if (!allowGrowth) {
                throw new IllegalStateException("Bad implementation where growth needed to happen twice. " +
                        "This will only occur if after growth we have high collision factor still, not " +
                        "spreading the values as it should. If this happen, FIND A BETTER HASH ALGORITHM");
            }

            hashTable = grow();
            pos = findHashPosition(value, hashTable);
            chain = findChain(pos, hashTable);
        }

        chain.insertLast(value);
        hashTable[pos] = chain;
        return true;
    }

    /**
     * O(1) (shrink not implemented)
     */
    @Override
    public T delete(T value) {
        int pos = findHashPosition(value, hashTable);
        LinkedSequence<T> chain = findChain(pos, hashTable);

        if (chain.size() == 0) {
            return null;
        }

        for (int i = 0; i < chain.size(); i++) {
            T refToValue = chain.at(i);
            if (Objects.equals(value, refToValue)) {
                chain.deleteAt(i);
                size--;
                return refToValue;
            }
        }

        return null;
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
            T value = findChain(i, hashTable).first();
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
            T value = findChain(i, hashTable).last();
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
    public T findPrevious(T value) {
        int chainPos = findHashPosition(value, hashTable);
        LinkedSequence<T> chain = findChain(chainPos, hashTable);

        boolean found = false;
        // go backwards
        for (int i = chain.size() - 1; i >= 0; i--) {
            T current = chain.at(i);
            if (!found && Objects.equals(value, current)) {
                found = true;
                continue;
            }

            // if it was found in the previous run, return previous;
            if (found) {
                return current;
            }
        }

        // If it was not found null
        if (!found) {
            return null;
        }

        // if it was found but not returned, it's in some previous chain;
        for (int chainIdx = chainPos - 1; chainIdx >= 0; chainIdx--) {
            LinkedSequence<T> nextChain = findChain(chainIdx, hashTable);
            if (nextChain.size() > 0) {
                return nextChain.last();
            }
        }

        // This will happen in case there is no next
        return null;
    }

    /**
     * O(n)
     */
    @Override
    public T findNext(T value) {
        int chainPos = findHashPosition(value, hashTable); //O(1)
        LinkedSequence<T> chain = findChain(chainPos, hashTable); //O(1)

        boolean found = false;
        for (int i = 0; i < chain.size(); i++) { // O(10)
            T current = chain.at(i);
            if (!found && Objects.equals(value, current)) {
                found = true;
                continue;
            }

            // if it was found in the previous run, just return it;
            if (found) {
                return current;
            }
        }

        // If it was not found null
        if (!found) {
            return null;
        }

        // if it was found but not returned, it's in some next chain;
        for (int chainIdx = chainPos + 1; chainIdx < hashTable.length; chainIdx++) { //O(n)
            LinkedSequence<T> nextChain = findChain(chainIdx, hashTable);
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

    public int capacity() {
        return hashTable.length;
    }

    private LinkedSequence<T> findChain(int pos, Object[] hashTable) {
        LinkedSequence<T> chain;
        if (hashTable[pos] == null) {
            chain = new LinkedSequence<T>();
        } else {
            chain = (LinkedSequence<T>) hashTable[pos];
        }

        return chain;
    }

    /**
     * O(1)
     * I know this is dump and very collision prone. I'm doing for simplicity
     */
    private int findHashPosition(T value, Object[] hashTable) {
        return Math.abs(Objects.hash(value) % hashTable.length);
    }

    /**
     * O(n)
     */
    private Object[] grow() {
        int newCapacity = GROWTH_RATIO * hashTable.length;
        Object[] newHashTable = new Object[newCapacity];

        T val = first();
        while (val != null) {
            add(newHashTable, val, false);
            val = findNext(val);
        }

        this.hashTable = newHashTable;
        return newHashTable;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(size * 2 + 2);

        builder.append("<");
        boolean appendComma = false;
        for (int i = 0; i < hashTable.length; i++) {
            if (findChain(i, hashTable).size() == 0) {
                continue;
            }

            if (appendComma) {
                builder.append(",");
            }

            builder.append(findChain(i, hashTable));
            appendComma = true;
        }

        builder.append(">");

        return builder.toString();
    }
}
