package datastructures.exercises;

import datastructures.exercises.collage.Collage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollageTest {

    @Test
    void maintainsInsertOrder() {
        Collage collage = new Collage();
        collage.importImage(1);
        collage.importImage(2);
        collage.importImage(3);
        assertEquals("3,2,1", collage.display());
    }

    @Test
    void changesLayerTwo() {
        Collage collage = new Collage();
        collage.importImage(1);
        collage.importImage(2);
        assertEquals("2,1", collage.display());

        collage.moveBelow(2, 1);
        assertEquals("1,2", collage.display());

    }
    @Test
    void nothingChanges() {
        Collage collage = new Collage();
        collage.importImage(1);
        collage.importImage(2);
        assertEquals("2,1", collage.display());

        collage.moveBelow(1, 2);
        assertEquals("2,1", collage.display());

    }


    @Test
    void changesLayerMiddle() {
        Collage collage = new Collage();
        collage.importImage(1);
        collage.importImage(2);
        collage.importImage(3);
        collage.importImage(4);
        assertEquals("4,3,2,1", collage.display());

        collage.moveBelow(3, 2);
        assertEquals("4,2,3,1", collage.display());

    }

    @Test
    void changesLayerMiddleThingsInBetween() {
        Collage collage = new Collage();
        collage.importImage(1);
        collage.importImage(2);
        collage.importImage(3);
        collage.importImage(4);
        collage.importImage(5);
        collage.importImage(6);
        collage.importImage(7);
        assertEquals("7,6,5,4,3,2,1", collage.display());

        collage.moveBelow(6, 2);
        assertEquals("7,5,4,3,2,6,1", collage.display());

    }

    @Test
    void changesLayerHeadToEnd() {
        Collage collage = new Collage();
        collage.importImage(1);
        collage.importImage(2);
        collage.importImage(3);
        collage.importImage(4);
        collage.importImage(5);
        collage.importImage(6);
        collage.importImage(7);
        assertEquals("7,6,5,4,3,2,1", collage.display());

        collage.moveBelow(7, 1);
        assertEquals("6,5,4,3,2,1,7", collage.display());
    }

    @Test
    void moveTailUp() {
        Collage collage = new Collage();
        collage.importImage(1);
        collage.importImage(2);
        collage.importImage(3);
        collage.importImage(4);
        collage.importImage(5);
        collage.importImage(6);
        collage.importImage(7);
        assertEquals("7,6,5,4,3,2,1", collage.display());

        collage.moveBelow(1, 7);
        assertEquals("7,1,6,5,4,3,2", collage.display());
    }

    @Test
    void moveUpMiddle() {
        Collage collage = new Collage();
        collage.importImage(1);
        collage.importImage(2);
        collage.importImage(3);
        collage.importImage(4);
        collage.importImage(5);
        collage.importImage(6);
        collage.importImage(7);
        assertEquals("7,6,5,4,3,2,1", collage.display());

        collage.moveBelow(3, 6);
        assertEquals("7,6,3,5,4,2,1", collage.display());
    }

}