package concurrency;

public class App {

    public static void main(String[] args) {
        SubLock subLock = new SubLock();



        for (int i = 0; i < 20; i++) {
            Thread thread = new Print(subLock);
            thread.setName("Thread" + i);
            thread.start();
        }
    }

    static class Print extends Thread {

        private SubLock lock;

        public Print(SubLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.print();
        }
    }
}
