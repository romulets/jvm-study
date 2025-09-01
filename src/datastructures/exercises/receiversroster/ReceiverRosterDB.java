package datastructures.exercises.receiverroster;

import datastructures.set.BinaryTreeSet;

public class ReceiverRosterDB {

    BinaryTreeSet<Receiver> receivers;

    public void record(int gameId, int receiverId, int points) {
        // Always remove and add again to ensure we are sorted
        Receiver receiver = receivers.delete(Receiver.ref(receiverId)); // O(log(n))
        if (receiver == null) {
            receiver = new Receiver(receiverId);
        }

        receiver.addGames(new ReceiverGame(gameId, points));

        receivers.add(receiver);
    }

    public void clear(int gameId, int receiverId) {

    }

    public ConsolidateReceiver[] rankedReceivers(int topN) {
        Receiver[] topReceivers = receivers.top(topN);
        ConsolidateReceiver[] consolidated = new ConsolidateReceiver[topN];
        int i = 0;
        for (Receiver receiver : topReceivers) {
            consolidated[i] = receiver.consolidate();
        }

        return consolidated;
    }

}
