package concurrency;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {

    private static class Counter {
        private long count = 0;
        public void inc() {
            count++;
        }

        public long get() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println((int)Math.ceil(1/2f));
        Lock lock = new ReentrantLock();
//        Lock lock = new ReentrantLock(true); // avoids starving but big overhead!

        Counter count = new Counter();

        OffsetDateTime start = OffsetDateTime.now();

        List<Thread> threads = new ArrayList<>(1000);
        for (int i = 0; i<1000; i++) {
           Thread t = Thread.ofVirtual().start(() -> {
                for (int j = 0; j < 1000; j++) {
                    try {
                        lock.lock();

                        // critical code
                        count.inc();
                        // end of critical code

                    } finally {
                        lock.unlock();
                    }
                }
            });

           threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }

        Duration took = Duration.between(start, OffsetDateTime.now());

        System.out.println(count.get());
        System.out.println("Took " + took.toMillis() + "ms");
    }
}
