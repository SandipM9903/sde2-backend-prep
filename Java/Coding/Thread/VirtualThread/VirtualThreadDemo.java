package Thread.VirtualThread;

public class VirtualThreadDemo {
    public static void main(String[] args) {
        // //Creating Thread
        // Thread t1 = new Thread(() -> {
        // System.out.println("Running Inside the Thread");
        // System.out.println("Thread Name is : " + Thread.currentThread().getName());
        // });

        // t1.start();
        // System.out.println("Thread Name is : " + Thread.currentThread().getName());

        // Now we will assign some work to Thread
        // Thread t2 = new Thread(() -> {
        // System.out.println("Task started");
        // try {
        // Thread.sleep(5000);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // System.out.println("Task finished");
        // });
        // t2.start();
        // System.out.println("Main finished");

        // Create 10,000 normal threads
        for (int i = 0; i < 10_000; i++) {

            Thread t = new Thread(() -> {
                System.out.println(
                        "Name: " + Thread.currentThread().getName()
                                + " | Virtual: " + Thread.currentThread().isVirtual());

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            t.start();
        }
    }
}
