package concurrency;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class VirtualThreadExample {

    public static class Operation {
        private long acc;
        public synchronized void inc() {
            this.acc++;
        }

        public synchronized long get() {
            return acc;
        }

    }

    public static void main(String[] args) {
        Operation op = new Operation();
        int vThreadCount = 500_000;
        List<Thread> vThreads = new ArrayList<>(vThreadCount);
        OffsetDateTime start = OffsetDateTime.now();
        for(int i=0; i<vThreadCount; i++) {
            Thread vThread = Thread.ofVirtual().start(()-> {
                for(int j=0; j<100_000;j++) {
                    op.inc();
                    op.get();
                }
            });

            vThreads.add(vThread);
        }

        for(Thread vThread : vThreads) {
            try {
                vThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Duration took = Duration.between(start, OffsetDateTime.now());
        System.out.println("Took " + took.toMillis() + "ms");
        System.out.println("Operations " + op.get());
    }
}
