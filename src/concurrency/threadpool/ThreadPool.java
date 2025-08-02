package concurrency.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {

    private final BlockingQueue<Runnable> taskQueue;
    private final List<PoolThreadRunnable> runnables;
    private boolean isStopped;

    public ThreadPool(int nThreads, int maxNTasks) {
        isStopped = false;
        runnables = new ArrayList<>();
        taskQueue = new ArrayBlockingQueue<>(maxNTasks);

        for(int i=0; i<nThreads; i++) {
            PoolThreadRunnable runnable = new PoolThreadRunnable(taskQueue);
            runnables.add(runnable);
            Thread.ofVirtual().name("thread-", i).start(runnable);
        }
    }

    public synchronized boolean execute(Runnable task) {
        if(this.isStopped) {
            throw new IllegalStateException("ThreadPool is stopped");
        }

        return this.taskQueue.offer(task);
    }

    public synchronized void stop() {
        this.isStopped = true;
        for(PoolThreadRunnable runnable: runnables){
            runnable.doStop();
        }
    }
    public synchronized void waitUntilAllTasksFinished() {
        while (!taskQueue.isEmpty() || runnables.stream().anyMatch(PoolThreadRunnable::isExecutingTask)) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
