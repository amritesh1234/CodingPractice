package threading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class EvenOddPrintingWithReentrantLock {
    private volatile boolean isEven = true;
    private int counte = 0;
    private int counto = 1;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition evenCondition = lock.newCondition();
    private final Condition oddCondition = lock.newCondition();

    public static void main(String[] args) {
        EvenOddPrintingWithReentrantLock printing = new EvenOddPrintingWithReentrantLock();

        Thread even = new Thread(printing::printEven);

        Thread odd = new Thread(printing::printOdd);

        even.start();
        odd.start();

        try {
            even.join();
            odd.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printEven(){
        while (counte <= 100) {
            this.lock.lock();
            try {
                while (!isEven) {
                    this.evenCondition.await();
                }
                isEven = false;
                System.out.println(counte);
                counte += 2;
                this.oddCondition.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                this.lock.unlock();
            }
        }
    }

    private void printOdd(){
        while (counto <= 100) {
            this.lock.lock();
            try {
                while (isEven) {
                    this.oddCondition.await();
                }
                isEven = true;
                System.out.println(counto);
                counto += 2;
                this.evenCondition.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                this.lock.unlock();
            }
        }
    }
}
