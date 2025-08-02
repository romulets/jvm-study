package concurrency;

public class ThreadLocalExample {

    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        Thread t1 = Thread.ofVirtual().start(()-> {
           threadLocal.set("Val 1");
           waitMillis(2000);
            System.out.println("Thread 1 " + threadLocal.get());
        });

        Thread t2 = Thread.ofVirtual().start(()-> {
            threadLocal.set("Val 2");
            waitMillis(1000);
            System.out.println("Thread 2 " + threadLocal.get());
        });

        Thread t3 = Thread.ofVirtual().start(()-> {
            threadLocal.set("Val 3");
            waitMillis(1500);
            System.out.println("Thread 3 " + threadLocal.get());
        });

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void waitMillis(long timeoutMillis) {
        try {
            Thread.sleep(timeoutMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
