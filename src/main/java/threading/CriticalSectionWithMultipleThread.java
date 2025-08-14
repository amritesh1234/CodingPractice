package threading;

import java.util.concurrent.Semaphore;

public class CriticalSectionWithMultipleThread {

    // At most 3 threads allowed in the critical section at once.
    // 'true' makes it fair (FIFO) to reduce starvation.
    private final Semaphore limit = new Semaphore(3, true);

    private void criticalSection() {
        try {
            // Acquire a permit before entering the critical section
            limit.acquire();
            try {
                System.out.println("Thread " + Thread.currentThread().getName() + " is running");
                Thread.sleep(5000);
            } finally {
                // Always release the permit when leaving
                limit.release();
            }
        } catch (InterruptedException e) {
            // Restore interrupt status and exit
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        CriticalSectionWithMultipleThread c = new CriticalSectionWithMultipleThread();
        Thread t1 = new Thread(c::criticalSection, "Thread-1");
        Thread t2 = new Thread(c::criticalSection, "Thread-2");
        Thread t3 = new Thread(c::criticalSection, "Thread-3");
        Thread t4 = new Thread(c::criticalSection, "Thread-4");
        Thread t5 = new Thread(c::criticalSection, "Thread-5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}