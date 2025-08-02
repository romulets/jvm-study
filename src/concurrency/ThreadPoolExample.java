package concurrency;

import concurrency.threadpool.ThreadPool;

import java.time.OffsetDateTime;

public class ThreadPoolExample {

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(20,10);

        for (int i=0; i<10; i++) {
            int taskNo = i;
            threadPool.execute(()-> {
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
                }

                System.out.println("["+ OffsetDateTime.now() +"]" +
                        "["+Thread.currentThread().getName()+"]" +
                        " Task" + taskNo);
            });
        }

        threadPool.waitUntilAllTasksFinished();
        threadPool.stop();
    }
}
