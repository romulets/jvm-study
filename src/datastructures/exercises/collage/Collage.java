package datastructures.exercises.collage;

import datastructures.sequence.LinkedSequence;
import datastructures.map.ChainHashMap;

public class Collage {

    private LinkedSequence.Node<Integer> layers;
    private final ChainHashMap<Integer, LinkedSequence.Node<Integer>> cache;

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
            layers = new LinkedSequence.Node<>(image);
        } else {
            // insert in front of A
            // Create B
            LinkedSequence.Node<Integer> newLayer = new LinkedSequence.Node<>(image);
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

        LinkedSequence.Node<Integer> current = layers;
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
        LinkedSequence.Node<Integer> belowRef = cache.get(below); // O(1)
        if (belowRef == null) {
            return;
        }

        LinkedSequence.Node<Integer> aboveRef = cache.get(above); //O(1)
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
