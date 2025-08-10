package datastructures.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeTest {

    @Test
    void constructor() {
        AVLTree<String> tree = new AVLTree<>("a");
        assertEquals("a", tree.transversalOrder());
        assertEquals(1, tree.size());
    }

    @Test
    void insertFirst() {
        AVLTree<Character> tree = new AVLTree<>('a');

        tree = tree.insertFirst('b');
        tree = tree.insertFirst('c');
        tree = tree.insertFirst('d');
        tree = tree.insertFirst('f');
        tree = tree.insertFirst('g');
        tree = tree.insertFirst('h');
        tree = tree.insertFirst('i');
        tree = tree.insertFirst('j');
        tree = tree.insertFirst('k');
        tree = tree.insertFirst('l');
        tree = tree.insertFirst('m');
        tree = tree.insertFirst('n');

        assertEquals("n,m,l,k,j,i,h,g,f,d,c,b,a", tree.transversalOrder());
        assertEquals(13, tree.size());
        assertEquals(4, tree.height());
    }

    @Test
    void insertLast() {
        AVLTree<Character> tree = new AVLTree<>('a');

        tree = tree.insertLast('b');
        tree = tree.insertLast('c');
        tree = tree.insertLast('d');
        tree = tree.insertLast('f');
        tree = tree.insertLast('g');
        tree = tree.insertLast('h');
        tree = tree.insertLast('i');
        tree = tree.insertLast('j');
        tree = tree.insertLast('k');
        tree = tree.insertLast('l');
        tree = tree.insertLast('m');
        tree = tree.insertLast('n');

        assertEquals("a,b,c,d,f,g,h,i,j,k,l,m,n", tree.transversalOrder());
        assertEquals(13, tree.size());
        assertEquals(4, tree.height());
    }

    @Test
    void testFirst() {
        AVLTree<Character> tree = new AVLTree<>('a');
        assertEquals('a', tree.first().value());

        tree = tree.insertFirst('b');
        assertEquals('b', tree.first().value());
        tree = tree.insertFirst('c');
        assertEquals('c', tree.first().value());
        tree = tree.insertFirst('d');
        assertEquals('d', tree.first().value());
        tree = tree.insertFirst('f');
        assertEquals('f', tree.first().value());
        tree = tree.insertFirst('g');
        assertEquals('g', tree.first().value());
        tree = tree.insertFirst('h');
        assertEquals('h', tree.first().value());
        tree = tree.insertFirst('i');
        assertEquals('i', tree.first().value());
        tree = tree.insertFirst('j');
        assertEquals('j', tree.first().value());
        tree = tree.insertFirst('k');
        assertEquals('k', tree.first().value());
        tree = tree.insertFirst('l');
        assertEquals('l', tree.first().value());
        tree = tree.insertFirst('m');
        assertEquals('m', tree.first().value());
        tree = tree.insertFirst('n');
        assertEquals('n', tree.first().value());
    }


    @Test
    void testNext() {
        AVLTree<Character> tree = new AVLTree<>('a');

        tree = tree.insertLast('b');
        tree = tree.insertLast('c');
        tree = tree.insertLast('d');
        tree = tree.insertLast('e');
        tree = tree.insertLast('f');
        tree = tree.insertLast('g');
        tree = tree.insertLast('h');
        tree = tree.insertLast('i');
        tree = tree.insertLast('j');
        tree = tree.insertLast('k');
        tree = tree.insertLast('l');
        tree = tree.insertLast('m');
        tree = tree.insertLast('n');

        Character[] expected = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n'};

        AVLTree<Character> next = tree.first();
        for (Character c : expected) {
            assertNotNull(next, "next is null, expected " + c);
            assertEquals(c, next.value());
            next = next.next();
        }

        assertNull(next);
    }

    @Test
    void testPrevious() {
        AVLTree<Character> tree = new AVLTree<>('a');

        tree = tree.insertFirst('b');
        tree = tree.insertFirst('c');
        tree = tree.insertFirst('d');
        tree = tree.insertFirst('e');
        tree = tree.insertFirst('f');
        tree = tree.insertFirst('g');
        tree = tree.insertFirst('h');
        tree = tree.insertFirst('i');
        tree = tree.insertFirst('j');
        tree = tree.insertFirst('k');
        tree = tree.insertFirst('l');
        tree = tree.insertFirst('m');
        tree = tree.insertFirst('n');

        Character[] expected = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n'};
        Integer[] expectedSizes = new Integer[]{1, 3, 1, 7, 1, 3, 1, 14, 1, 3, 1, 6, 2, 1};
        Integer[] expectedHeight = new Integer[]{1, 2, 1, 3, 1, 2, 1, 4, 1, 2, 1, 3, 2, 1};

        AVLTree<Character> previous = tree.last();
        int i = 0;
        for (Character c : expected) {
            assertNotNull(previous, "previous is null, expected " + c);
            assertEquals(c, previous.value());
            assertEquals(expectedHeight[i], previous.height(), "Height of " + c + " differ");
            assertEquals(expectedSizes[i], previous.size(), "Size of " + c + " differ");

            previous = previous.previous();
            i++;
        }

        assertNull(previous);
    }


    @Test
    void testDeleteLeaf() {
        AVLTree<Character> tree = new AVLTree<>('a');

        tree = tree.insertLast('b');
        tree = tree.insertLast('c');
        tree = tree.insertLast('d');
        tree = tree.insertLast('e');
        tree = tree.insertLast('f');
        tree = tree.insertLast('g');
        tree = tree.insertLast('h');
        tree = tree.insertLast('i');
        tree = tree.insertLast('j');
        tree = tree.insertLast('k');
        tree = tree.insertLast('l');
        tree = tree.insertLast('m');
        tree = tree.insertLast('n');

        AVLTree<Character> aNode = tree.first();
        AVLTree<Character> newParent = aNode.delete();
        assertEquals("b,c,d,e,f,g,h,i,j,k,l,m,n", newParent.transversalOrder());
        assertEquals(13, newParent.size());
        assertEquals(4, newParent.height());

        AVLTree<Character> gNode = tree.first().next().next().next().next().next();
        newParent = gNode.delete();
        assertEquals("b,c,d,e,f,h,i,j,k,l,m,n", newParent.transversalOrder());
        assertEquals(12, newParent.size());
        assertEquals(4, newParent.height());

        AVLTree<Character> cNode = tree.first().next();
        newParent = cNode.delete();
        assertEquals("b,d,e,f,h,i,j,k,l,m,n", newParent.transversalOrder());
        assertEquals(11, newParent.size());
        assertEquals(4, newParent.height());

        //  should balance height!
        AVLTree<Character> bNode = tree.first();
        newParent = bNode.delete();
        assertEquals("d,e,f,h,i,j,k,l,m,n", newParent.transversalOrder());
        assertEquals(10, newParent.size());
        assertEquals(4, newParent.height());

        System.out.println(newParent.treeDiagram());
    }

}