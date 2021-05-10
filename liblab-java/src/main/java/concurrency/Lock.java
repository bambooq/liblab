package concurrency;

public class Lock {

    public static Object object = new Object();

    public void print() {
        System.out.println(Thread.currentThread().getName() + " Lock print...");
    }
}
