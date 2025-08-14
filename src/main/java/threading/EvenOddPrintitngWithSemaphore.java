package threading;

import java.util.concurrent.Semaphore;

public class EvenOddPrintitngWithSemaphore {

    private int counte = 0;
    private int counto = 1;

    private final Semaphore evenSemaphore = new Semaphore(1);
    private final Semaphore oddSemaphore = new Semaphore(0);

    public static void main(String[] args) {
        EvenOddPrintitngWithSemaphore printing = new EvenOddPrintitngWithSemaphore();

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

    private void printEven() {
        while (counte <= 100) {
            try {
                evenSemaphore.acquire();
                System.out.println(counte);
                counte += 2;
                oddSemaphore.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void printOdd() {
        while (counto <= 100) {
            try {
                oddSemaphore.acquire();
                System.out.println(counto);
                counto += 2;
                evenSemaphore.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
