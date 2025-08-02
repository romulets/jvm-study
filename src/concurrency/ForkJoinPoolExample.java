package concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolExample {

    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        try (pool) {
            for (int i = 0; i < 1_000_000; i++) {
                int num = i;
                pool.submit(RecursiveAction.adapt(() -> {
//                    System.out.println("num " + num);
                    for (int j = 0; j < 10; j++) {
                        int subnum = j;
                        RecursiveTask.adapt(() -> {
//                            System.out.println("num " + num + " subnum " + subnum);
                        }).fork();
                    }
                }));
            }
        }

    }
}
