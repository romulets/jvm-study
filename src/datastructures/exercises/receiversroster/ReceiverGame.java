package datastructures.exercises.receiverroster;

public record ReceiverGame(int id, int pointsInTheGame) implements Comparable<ReceiverGame> {
    @Override
    public int compareTo(ReceiverGame o) {
        return Integer.compare(this.id, o.id);
    }
}
