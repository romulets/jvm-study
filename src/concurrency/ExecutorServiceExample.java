package concurrency;

import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceExample {

    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                10,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(128),
                Thread.ofVirtual().factory()

        );

        try (executorService) {
            for (int i = 0; i < 10; i++) {
                int taskNum = i;
                executorService.execute(() -> {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println(Thread.currentThread().getName() + " Task " + taskNum);
                });
            }

            Future<String> future = executorService.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " Submitted");
                return "Completed";
            });



            String res = future.get();
            System.out.println("got value from future " + res);

            String invokeAnyRes = executorService.invokeAny(List.of(
                    () -> "1",
                    () -> "2",
                    () -> "3"
            ));

            System.out.println("got value from invoke any " + invokeAnyRes);


            executorService.shutdown();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
