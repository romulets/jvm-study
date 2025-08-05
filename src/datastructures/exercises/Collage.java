package datastructures.exercises;

import datastructures.array.LinkedArray;
import datastructures.map.ChainHashMap;

/**
 * Problem 1-3. Collage Collating
 * Fodoby is a company that makes customized software tools for creative people. Their newest
 * software, Ottoshop, helps users make collages by allowing them to overlay images on top of each
 * other in a single document. Describe a database to keep track of the images in a given document
 * which supports the following operations:
 * 1. make document(): construct an empty document containing no images
 * 2. import image(x): add an image with unique integer ID x to the top of the document
 * 3. display(): return an array of the document’s image IDs in order from bottom to top
 * 4. move below(x, y): move the image with ID x directly below the image with ID y
 * Operation (1) should run in worst-case O(1) time, operations (2) and (3) should each run in worst-
 * case O(n) time, while operation (4) should run in worst-case O(log n) time, where n is the number
 * of images contained in a document at the time of the operation.
 */
public class Collage {

    private LinkedArray.Node<Integer> layers;
    private final ChainHashMap<Integer, LinkedArray.Node<Integer>> cache;

    /**
     * O(1)
     */
    public Collage() {
        layers = null;
        cache = new ChainHashMap<>();
    }

    /**
     * Actually amortized O(1)????
     */
    public void importImage(Integer image) {
        if (layers == null) {
            layers = new LinkedArray.Node<>(image);
        } else {
            // insert in front of A
            // Create B
            LinkedArray.Node<Integer> newLayer = new LinkedArray.Node<>(image);
            // B -> A
            newLayer.next = layers;
            // A <- B
            layers.previous = newLayer;
            // override head
            layers = newLayer;
        }

        // reference to head of layer
        cache.add(image, layers);
    }

    /**
     * O(n)
     */
    public String display() {
        StringBuilder builder = new StringBuilder(cache.size() * 2);

        LinkedArray.Node<Integer> current = layers;
        while (current != null) {
            builder.append(current.value);
            current = current.next;
            if (current != null) {
                builder.append(",");
            }
        }

        return builder.toString();
    }

    /**
     * O(1)???
     */
    public void moveBelow(Integer below, Integer above) {
        LinkedArray.Node<Integer> belowRef = cache.get(below); // O(1)
        if (belowRef == null) {
            return;
        }

        LinkedArray.Node<Integer> aboveRef = cache.get(above); //O(1)
        if (aboveRef == null) {
            return;
        }

        // If it's head, we need to keep consistency in the
        // linked list
        if (belowRef.previous == null) {
            layers = layers.next;
            layers.previous = null;
        } else {
            // Stitch previous to next
            belowRef.previous.next = belowRef.next;
        }

        // scenario B will be inserted between A and C

        // if not tail
        if (aboveRef.next != null) {
            // point B <- C
            aboveRef.next.previous = belowRef;
            // point B -> C
            belowRef.next = aboveRef.next;
        } else {
            // point B -> null because it was tail
            belowRef.next = null;
        }

        // point A -> B
        aboveRef.next = belowRef;
        // point A <- B
        belowRef.previous = aboveRef;

        // So we have A <-> B <-> C (unless c is null)
    }
}
