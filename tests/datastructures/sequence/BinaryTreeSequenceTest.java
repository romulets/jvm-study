package datastructures.sequence;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeSequenceTest extends SequenceTestCase{

    BinaryTreeSequenceTest() {
        super(BinaryTreeSequence::new, BinaryTreeSequence::new, BinaryTreeSequence::new);
    }
}