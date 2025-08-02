package concurrency.threadpool;

import java.util.concurrent.BlockingQueue;

public class PoolThreadRunnable implements Runnable{

    private Thread thread;
    private final BlockingQueue<Runnable> taskQueue;
    private boolean isStopped;
    private boolean isExecutingTask;

    public PoolThreadRunnable(BlockingQueue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped) {
            Runnable task = taskQueue.poll();
            if (task!=null) {
                isExecutingTask = true;
                task.run();
            }
            isExecutingTask = false;
        }
    }

    public synchronized void doStop() {
        isStopped = true;
        thread.interrupt();
    }

    public synchronized boolean isExecutingTask() {
        return isExecutingTask;
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}
