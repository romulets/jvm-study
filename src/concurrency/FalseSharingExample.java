package concurrency;

import jdk.internal.vm.annotation.Contended;

public class FalseSharingExample {
    @Contended
    public static class Counters {
        public volatile int a;
        public volatile int b;
    }

    public static void main(String[] args) throws InterruptedException {
        Counters first = new Counters();
        Counters second = new Counters();

        long it = 1_000_000_000;

        Thread t1 = Thread.ofVirtual().start(() -> {
            long now = System.currentTimeMillis();
            for (long i = 0; i<it; i++) {
                first.a++;
            }
            System.out.println(System.currentTimeMillis() - now);
        });

        Thread t2 = Thread.ofVirtual().start(() -> {
            long now = System.currentTimeMillis();
            for (long i = 0; i<it; i++) {
                second.b++;
            }
            System.out.println(System.currentTimeMillis() - now);
        });

        t1.join();
        t2.join();
    }
}
