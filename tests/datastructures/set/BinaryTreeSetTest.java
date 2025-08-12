package datastructures.set;

import org.junit.jupiter.api.Test;

import static datastructures.tree.AVLTreePrint.printTree;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryTreeSetTest extends SetTestCases {

    protected BinaryTreeSetTest() {
        super(
                BinaryTreeSet::new,
                size -> new BinaryTreeSet<>(),
                BinaryTreeSet::new
        );
    }

    @Test
    void insertAlwaysInOrder() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();
        set.add(1);

        assertEquals("1", set.tree.transversalOrder());
        assertEquals(1, set.size());

        set.add(1);
        assertEquals(1, set.size());

        set.add(2);
        assertEquals("1,2", set.tree.transversalOrder());
        assertEquals(2, set.size());

        set.add(3);
        assertEquals("1,2,3", set.tree.transversalOrder());
        assertEquals(3, set.size());

        set.add(3);
        assertEquals("1,2,3", set.tree.transversalOrder());
        assertEquals(3, set.size());

        set.add(7);
        assertEquals("1,2,3,7", set.tree.transversalOrder());
        assertEquals(4, set.size());

        set.add(10);
        assertEquals("1,2,3,7,10", set.tree.transversalOrder());
        assertEquals(5, set.size());

        set.add(27);
        assertEquals("1,2,3,7,10,27", set.tree.transversalOrder());
        assertEquals(6, set.size());

        set.add(28);
        set.add(29);
        set.add(30);
        assertEquals("1,2,3,7,10,27,28,29,30", set.tree.transversalOrder());
        assertEquals(9, set.size());
    }

    @Test
    void insertRandomPlaces() {
        BinaryTreeSet<Integer> set = new BinaryTreeSet<>();

        set.add(8);
        assertEquals("8", set.tree.transversalOrder());
        assertEquals(1, set.size());

        set.add(1);
        assertEquals("1,8", set.tree.transversalOrder());
        assertEquals(2, set.size());

        set.add(5);
        assertEquals("1,5,8", set.tree.transversalOrder());
        assertEquals(3, set.size());

        set.add(7);
        assertEquals("1,5,7,8", set.tree.transversalOrder());
        assertEquals(4, set.size());

        set.add(6);
        assertEquals("1,5,6,7,8", set.tree.transversalOrder());
        assertEquals(5, set.size());

        set.add(2);
        assertEquals("1,2,5,6,7,8", set.tree.transversalOrder());
        assertEquals(6, set.size());

        set.add(-1);
        assertEquals("-1,1,2,5,6,7,8", set.tree.transversalOrder());
        assertEquals(7, set.size());

        set.add(3);
        assertEquals("-1,1,2,3,5,6,7,8", set.tree.transversalOrder());
        assertEquals(8, set.size());

        set.add(4);
        assertEquals("-1,1,2,3,4,5,6,7,8", set.tree.transversalOrder());
        assertEquals(9, set.size());

        set.add(0);
        set.add(0);
        set.add(0);
        set.add(0);
        set.add(0);
        assertEquals("-1,0,1,2,3,4,5,6,7,8", set.tree.transversalOrder());
        assertEquals(10, set.size());

        set.add(9);
        assertEquals("-1,0,1,2,3,4,5,6,7,8,9", set.tree.transversalOrder());
        assertEquals(11, set.size());

        printTree(set.tree);
    }

    @Test
    void findNext() {
        Set<String> set = new BinaryTreeSet<>(new String[]{"b", "d", "a", "c", "f", "e"});
        String[] expectedSequence = new String[]{"a", "b", "c", "d", "e", "f"};

        String next = set.first();
        for (String val : expectedSequence) {
            assertEquals(next, val);
            next = set.findNext(next);
        }
    }

    @Test
    void findPrevious() {
        Set<String> set = new BinaryTreeSet<>(new String[]{"b", "d", "a", "c", "f", "e"});

        String[] expectedSequence = new String[]{"f", "e", "d", "c", "b", "a"};

        String last = set.last();
        for (String val : expectedSequence) {
            assertEquals(last, val);
            last = set.findPrevious(last);
        }
    }
}
