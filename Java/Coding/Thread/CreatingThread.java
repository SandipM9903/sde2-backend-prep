package Thread;

//First we will create Thread by extending Thread class
class MyThread extends Thread {
    // Constructor to set thread name
    public MyThread(String name) {
        super(name);
    }
    public void run() {
        System.out.println("Running in: " + Thread.currentThread().getName());
    }
}

public class CreatingThread {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("SANDIP");
        t1.run(); // it will run in main thread
        t1.start(); // it will create a new thread
    }
}
