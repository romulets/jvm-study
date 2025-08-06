package datastructures.set;

import java.util.Arrays;
import java.util.Objects;

/**
 * For Simplicity, let's use quadratic probing.
 * Approaches I know:
 * - Linear probing (easy to cluster)
 * - Quadratic probing (spreads data a bit more)
 * - Double Hashing (best data distribution)
 *
 * CLUSTERING IS HITTING HARD, is it a me problem or just a probing concept problem?
 * @param <T>
 */
public class OpenAddressedHashSet<T> implements Set<T> {

    private static final Object THUMB_STONE = new Object();

    private static final int DEFAULT_CAPACITY = 64;
    private static final int GROWTH_RATIO = 2;
    private int size;
    private Object[] hashTable;

    /**
     * O(1)
     */
    public OpenAddressedHashSet(int capacity) {
        hashTable = new Object[capacity];
        size = 0;
    }

    /**
     * O(1)
     */
    public OpenAddressedHashSet() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * O(n)
     */
    public OpenAddressedHashSet(T[] values) {
        if (values == null || values.length == 0) {
            hashTable = new Object[DEFAULT_CAPACITY];
        } else {
            hashTable = new Object[values.length];
        }

        if (values != null) {
            for (T value : values) {
                add(value);
            }
        }
    }

    @Override
    public boolean contains(T value) {
        return find(value) != null;
    }

    /**
     * O(log(n)) if probing needed
     */
    public T find(T value) {
        int pos = findPos(value);
        if (pos == -1) {
            return  null;
        }
        return (T) hashTable[pos];
    }

    /**
     * O(log(n)) if probing needed
     */
    public int findPos(T value) {
        int pos = findHashPosition(value, hashTable);

        // If it's null it's because it was never seen
        if (hashTable[pos] == null) {
            return -1;
        }

        // found object!
        if (Objects.equals(hashTable[pos], value) ) {
            return pos;
        }

        // start probing
        int probFactor = 1;
        boolean backAtBeginning = false;
        while(true) {
            int probPos = findHashPosition(pos + probFactor, hashTable);

            // If it's not even a thumbstone, it's not found
            if (hashTable[probPos] == null) {
                return -1;
            }

            // If the object is there, return value
            if (Objects.equals(hashTable[probPos], value)) {
                return probPos;
            }

            // Square factor
            int newProbFactor = (probFactor * 2);
            int potentialNewProbPos = pos + newProbFactor;

            // If the new prob pos is larger than hash table
            // we are starting from the beginning of the array
            // we need to know it to prevent infinite loops
            if (!backAtBeginning && potentialNewProbPos >= hashTable.length) {
                backAtBeginning = true;

                // if we are back at the begging and the new prob pos is larger than
                // the initial hashed pos, there is no value.
            } else if (backAtBeginning && probPos >= pos) {
                break;
            }
            probFactor = newProbFactor;
        }

        return -1;
    }

    /**
     * For complexity see OpenAddressedHasSet::add
     */
    @Override
    public void add(T value) {
        if (add(value, hashTable, true)) {
            size++;
        }
    }

    /**
     * Amortized O(log(n)) when hits probing (amortized on growth that is O(n)
     */
    public boolean add(T value, Object[] hashTable, boolean allowGrowth) {
        int pos = findHashPosition(value, hashTable);

        // Is empty
        if (emptyPos(pos, hashTable)) {
            hashTable[pos] = value;
            return true;
        }

        // it's the same
        if (Objects.equals(hashTable[pos], value)) {
            return false;
        }

        // it's a collision, we need to find a new position
        // We are solving by quadratic probing
        int probFactor = 1;
        boolean probing = true;
        boolean backAtBeginning = false;
        while(probing) {
            int probPos = findHashPosition(pos + probFactor, hashTable);

            // If the position is empty, set it there!
            if (emptyPos(probPos, hashTable)) {
                hashTable[probPos] = value;
                return true;
            }

            // Square factor
            int newProbFactor = (probFactor * 2);
            int potentialNewProbPos = pos + newProbFactor;

            // If the new prob pos is larger than hash table
            // we are starting from the beginning of the array
            // we need to know it to prevent infinite loops
            if (!backAtBeginning == potentialNewProbPos >= hashTable.length) {
                backAtBeginning = true;

            // if we are back at the begging and the new prob pos is larger than
            // the initial hashed pos, we need to grow.
            } else if (backAtBeginning && probPos >= pos) {
                probing = false;
            }
            probFactor = newProbFactor;
        }

        // If we arrived here, it's because we could not find a position.
        if (!allowGrowth) {
            throw new IllegalStateException("Bad implementation where growth needed to happen twice. " +
                    "This will only occur if after growth we have high collision factor still, not " +
                    "spreading the values as it should. If this happen, FIND A BETTER HASH ALGORITHM");
        }

        hashTable = grow();
        // One level only recursion. Should not be too bad for stack
        // and makes concept+code so much simpler
        return add(value, hashTable, false);
    }

    /**
     * O(log(n)) if probing needed
     */
    @Override
    public void delete(T value) {
        int pos = findPos(value);
        if (pos == -1) {
            return;
        }

        hashTable[pos] = THUMB_STONE;
        size--;
    }

    /**
     * O(n)
     * @return
     */
    @Override
    public T first() {
        if (size == 0) {
            return null;
        }

        for (int i = 0; i < hashTable.length; i++) {
            if (!emptyPos(i, hashTable)) {
                return (T) hashTable[i];
            }
        }

        // should never happen
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
            if (!emptyPos(i, hashTable)) {
                return (T) hashTable[i];
            }
        }

        // should never happen
        return null;
    }

    /**
     * O(n)
     */
    @Override
    public T findPrevious(T value) {
        int pos = findPos(value);
        if (pos <= 0) {
            return null;
        }

        for (int i = pos - 1; i >= 0; i--) {
            if (!emptyPos(i, hashTable)) {
                return (T) hashTable[i];
            }
        }

        return null;
    }

    /**
     * O(n)
     */
    @Override
    public T findNext(T value) {
        int pos = findPos(value);
        if (pos == -1 || pos > hashTable.length - 1) {
            return null;
        }

        for (int i = pos + 1; i < hashTable.length; i++) {
            if (!emptyPos(i, hashTable)) {
                return (T) hashTable[i];
            }
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    public int capacity() {
        return hashTable.length;
    }

    /**
     * O(1)
     * I know this is dump and very collision prone. I'm doing for simplicity
     */
    private int findHashPosition(T value, Object[] hashTable) {
        return findHashPosition(Objects.hash(value), hashTable);
    }

    private int findHashPosition(int hash, Object[] hashTable) {
        return Math.abs(hash % hashTable.length);
    }

    /**
     * O(n)
     */
    private Object[] grow() {
        int newCapacity = GROWTH_RATIO * hashTable.length;
        Object[] newHashTable = new Object[newCapacity];

        T val = first();
        while (val != null) {
            add(val, newHashTable, false);
            val = findNext(val);
        }

        this.hashTable = newHashTable;
        return newHashTable;
    }

    /**
     * We gotta check for thumbstone too
     */
    private boolean emptyPos(int pos, Object[] hashTable) {
        return hashTable[pos] == null || hashTable[pos] == THUMB_STONE;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.stream(hashTable).filter(Objects::nonNull).toArray());
    }
}
