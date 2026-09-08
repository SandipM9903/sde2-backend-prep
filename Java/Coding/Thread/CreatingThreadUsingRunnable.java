package Thread;

public class CreatingThreadUsingRunnable {
    public static void main(String[] args) {
        Runnable r1 = new Runnable() {
            public void run() {
                System.out.println("Running in: " + Thread.currentThread().getName());
            }
        };

        Thread t1 = new Thread(r1, "T2-Thread Name"); // Here we have assigned the thread name so we wrote 2 arguments
                                                      // (r1, "T2-Thread Name") or we can also write (r1)
        t1.start();
    }
}
