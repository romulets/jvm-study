package datastructures.tree;

import org.junit.jupiter.api.Test;

import static datastructures.tree.AVLTreePrint.treeDiagram;
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

        assertTreeHasOrderAndProps(tree, "n,m,l,k,j,i,h,g,f,d,c,b,a", 13, 4);
    }

    @Test
    void testRightRotation() {
        AVLTree<Character> tree = new AVLTree<>('a');
        tree = tree.insertFirst('b');
        tree = tree.insertFirst('c');


        assertTreeHasOrderAndProps(tree, "c,b,a", 3, 2);
    }

    @Test
    void testLeftRotation() {
        AVLTree<Character> tree = new AVLTree<>('a');
        tree = tree.insertLast('b');
        tree = tree.insertLast('c');


        assertTreeHasOrderAndProps(tree, "a,b,c", 3, 2);
    }

    @Test
    void testLeftRightRotation() {
        AVLTree<Character> tree = new AVLTree<>('a');
        tree = tree.insertLast('b');
        tree = tree.insertLast('c');
        tree = tree.insertLast('d');
        tree = tree.insertLast('e');
        tree = tree.insertLast('f');

        assertTreeHasOrderAndProps(tree, "a,b,c,d,e,f", 6, 3);
        tree = tree.first().deleteNode(); // delete a
        tree = tree.last().deleteNode(); // delete f
        tree = tree.last().deleteNode(); // delete e

        assertTreeHasOrderAndProps(tree, "b,c,d", 3, 2);
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

        assertTreeHasOrderAndProps(tree, "a,b,c,d,f,g,h,i,j,k,l,m,n", 13, 4);
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

        tree = tree.first().deleteNode(); // delete a
        assertTreeHasOrderAndProps(tree, "b,c,d,e,f,g,h,i,j,k,l,m,n", 13, 4);
        tree = tree.transversalOrderAt(5).deleteNode(); // delete g
        assertTreeHasOrderAndProps(tree, "b,c,d,e,f,h,i,j,k,l,m,n", 12, 4);
        tree = tree.first().next().deleteNode(); // delete c
        assertTreeHasOrderAndProps(tree, "b,d,e,f,h,i,j,k,l,m,n", 11, 4);
        tree = tree.first().deleteNode(); // delete b
        assertTreeHasOrderAndProps(tree, "d,e,f,h,i,j,k,l,m,n", 10, 4);
        tree = tree.last().deleteNode(); // delete n
        assertTreeHasOrderAndProps(tree, "d,e,f,h,i,j,k,l,m", 9, 4);
        tree = tree.last().deleteNode(); // delete m
        assertTreeHasOrderAndProps(tree, "d,e,f,h,i,j,k,l", 8, 4);
        tree = tree.transversalOrderAt(2).deleteNode(); // delete f
        assertTreeHasOrderAndProps(tree, "d,e,h,i,j,k,l", 7, 4);
        tree = tree.first().deleteNode(); // delete d
        assertTreeHasOrderAndProps(tree, "e,h,i,j,k,l", 6, 3);
        tree = tree.last().previous().deleteNode(); // delete k
        assertTreeHasOrderAndProps(tree, "e,h,i,j,l", 5, 3);
        tree = tree.first().deleteNode(); // delete e
        assertTreeHasOrderAndProps(tree, "h,i,j,l", 4, 3);
        tree = tree.first().next().deleteNode(); // delete i
        assertTreeHasOrderAndProps(tree, "h,j,l", 3, 2);
        tree = tree.first().deleteNode(); // delete h
        assertTreeHasOrderAndProps(tree, "j,l", 2, 2);
        tree = tree.last().deleteNode(); // delete l
        assertTreeHasOrderAndProps(tree, "j", 1, 1);
        tree = tree.deleteNode();
        assertNull(tree);
    }

    @Test
    void testDeleteHead() {
        AVLTree<Character> tree = new AVLTree<>('a');
        tree = tree.insertLast('b');
        tree = tree.insertLast('c');
        tree = tree.insertLast('d');
        tree = tree.insertLast('e');
        tree = tree.insertLast('f');

        assertTreeHasOrderAndProps(tree, "a,b,c,d,e,f", 6, 3);

        // delete head
        tree = tree.deleteNode();

        assertTreeHasOrderAndProps(tree, "a,b,c,e,f", 5, 3);
    }

    @Test
    void testDeleteMultiple() {
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

        System.out.println(treeDiagram(tree));

        tree = tree.deleteNode(); // deleting H
        assertTreeHasOrderAndProps(tree, "a,b,c,d,e,f,g,i,j,k,l,m,n", 13, 4);
        tree = tree.transversalOrderAt(10).deleteNode(); // deleting L
        assertTreeHasOrderAndProps(tree, "a,b,c,d,e,f,g,i,j,k,m,n", 12, 4);
        tree = tree.transversalOrderAt(10).deleteNode(); // deleting M
        assertTreeHasOrderAndProps(tree, "a,b,c,d,e,f,g,i,j,k,n", 11, 4);
        tree = tree.transversalOrderAt(9).deleteNode(); // deleting K
        assertTreeHasOrderAndProps(tree, "a,b,c,d,e,f,g,i,j,n", 10, 4);
        tree = tree.last().deleteNode(); // deleting N
        assertTreeHasOrderAndProps(tree, "a,b,c,d,e,f,g,i,j", 9, 4);
        tree = tree.last().deleteNode(); // deleting J
        assertTreeHasOrderAndProps(tree, "a,b,c,d,e,f,g,i", 8, 4);
        tree = tree.deleteNode(); // deleting D
        assertTreeHasOrderAndProps(tree, "a,b,c,e,f,g,i", 7, 3);
        tree = tree.deleteNode(); // deleting E
        assertTreeHasOrderAndProps(tree, "a,b,c,f,g,i", 6, 3);
        tree = tree.deleteNode(); // deleting F
        assertTreeHasOrderAndProps(tree, "a,b,c,g,i", 5, 3);
        tree = tree.deleteNode(); // deleting g
        assertTreeHasOrderAndProps(tree, "a,b,c,i", 4, 3);
        tree = tree.deleteNode(); // deleting b
        assertTreeHasOrderAndProps(tree, "a,c,i", 3, 2);
        tree = tree.deleteNode(); // deleting c
        assertTreeHasOrderAndProps(tree, "a,i", 2, 2);
        tree = tree.deleteNode(); // deleting i
        assertTreeHasOrderAndProps(tree, "a", 1, 1);
        tree = tree.deleteNode(); // deleting a
        assertNull(tree);
    }

    private static void assertTreeHasOrderAndProps(AVLTree<Character> tree, String transversalOrder, int size, int height) {
        assertEquals(transversalOrder, tree.transversalOrder());
        assertEquals(size, tree.size());
        assertEquals(height, tree.height());
    }

}