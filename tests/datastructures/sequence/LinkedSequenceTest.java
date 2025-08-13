package datastructures.sequence;

import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class LinkedSequenceTest extends SequenceTestCase {

    LinkedSequenceTest() {
        super(LinkedSequence::new, LinkedSequence::new, LinkedSequence::new);
    }

}