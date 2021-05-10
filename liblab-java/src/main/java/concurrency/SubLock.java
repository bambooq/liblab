package concurrency;

public class SubLock extends Lock {

    @Override
    public void print() {
        synchronized(this) {
            System.out.println(Thread.currentThread().getName() + " print SubLock...");
            synchronized (this) {
                super.print();
            }
        }
    }
}
