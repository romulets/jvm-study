package datastructures.exercises.receiverroster;

import datastructures.tree.AVLTree;

public class GameAVLTree extends AVLTree<ReceiverGame> {

    private int totalPoints;

    public GameAVLTree(ReceiverGame value) {
        super(value);
    }

    @Override
    protected void updateComputedProperties() {
        super.updateComputedProperties();
        int pointsLeft = left() != null ? left().value().pointsInTheGame() : 0;
        int pointsRight = right() != null ? right().value().pointsInTheGame() : 0;
        totalPoints += pointsLeft + pointsRight + value().pointsInTheGame();
    }

    public int totalPoints() {
        return totalPoints;
    }
}
