package threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorServiceExample {

    // Allow at most 3 threads in the critical section at once (fair to reduce starvation)
    private final Semaphore limit = new Semaphore(3, true);

    private void criticalSection() {
        try {
            limit.acquire();
            try {
                System.out.println("Thread " + Thread.currentThread().getName() + " is running");
                Thread.sleep(5000);
            } finally {
                limit.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        ExecutorServiceExample c = new ExecutorServiceExample();

        // Optional: name threads like "Thread-1", "Thread-2", ...
        AtomicInteger idx = new AtomicInteger(1);
        ThreadFactory namedFactory = r -> new Thread(r, "Thread-" + idx.getAndIncrement());

        // Backing pool of 5 worker threads
        try (ExecutorService executor = Executors.newFixedThreadPool(5, namedFactory)) {

            try {
                // Submit 5 tasks; the semaphore limits concurrent entries to 3
                for (int i = 0; i < 5; i++) {
                    executor.submit(c::criticalSection);
                }
            } finally {
                executor.shutdown();
                try {
                    if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}