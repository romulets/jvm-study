package datastructures.exercises.receiverroster;

import datastructures.set.BinaryTreeSet;

import java.util.Objects;

public class Receiver implements Comparable<Receiver> {

    private final int id;
    private final BinaryTreeSet<ReceiverGame> games;

    public Receiver(int id) {
        this(id, true);
    }

    private Receiver(int id, boolean realInstance) {
        this.id = id;
        if (realInstance) {
            this.games = new BinaryTreeSet<>(GameAVLTree::new);
        } else {
            this.games = null;
        }
    }

    static Receiver ref(int id) {
        return new Receiver(id, false);
    }

    public int getTotalPoints() {
        ReceiverGame game = games().headValue();
        return game != null ? game.pointsInTheGame() : 0;
    }

    public int getAmountPlayedGames() {
        return games().size();
    }

    private BinaryTreeSet<ReceiverGame> games() {
        return Objects.requireNonNull(games, "Can't perform operations on receiver ref");
    }

    public void addGames(ReceiverGame game) {
        games().add(game);
    }

    @Override
    public int compareTo(Receiver o) {
        // compare cross multiplication to avoid integer comparison
        return Integer.compare(getTotalPoints() * o.getAmountPlayedGames(), o.getTotalPoints() * getAmountPlayedGames());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receiver receiver = (Receiver) o;
        return id == receiver.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ConsolidateReceiver consolidate() {
        return new ConsolidateReceiver(id, getTotalPoints(), getAmountPlayedGames());
    }
}
