package threading;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeQueue {

    LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        ThreadSafeQueue queue = new ThreadSafeQueue();

        Thread p1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                queue.queue.add(queue.counter.getAndIncrement());
                System.out.println("Added element_1: " + queue.counter.get());
            }
        });

        Thread p2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                queue.queue.add(queue.counter.getAndIncrement());
                System.out.println("Added element_2: " + queue.counter.get());
            }
        });

        System.out.println("Total elements in queue: " + queue.queue.size());


        Thread c1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                int r = queue.queue.remove();
                System.out.println("Removed element_1: " + r + ", remaining elements: " + queue.queue.size());
            }
        });

        Thread c2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                int r = queue.queue.remove();
                System.out.println("Removed element_2: " + r + ", remaining elements: " + queue.queue.size());
            }
        });

        System.out.println("starting threads");
        p1.start();
        p2.start();

        c1.start();
        c2.start();

        try {
            p1.join();
            p2.join();
            c1.join();
            c2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Total elements in queue: " + queue.queue.size());
    }
}
