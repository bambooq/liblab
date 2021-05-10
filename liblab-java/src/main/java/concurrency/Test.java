package concurrency;

/**
 * 证明：
 * 父类和子类的synchronized用同一把锁，且是子类锁
 */
public class Test {


    public static void main(String[] args) throws InterruptedException {
        final TestChild testChild = new TestChild();
        new Thread(() -> testChild.doSomething()).start();
        // 主线程睡眠100ms，保证子线程先执行
        Thread.sleep(100);
        // 如果不是同一把锁，不会等待子线程睡眠1000ms
        testChild.doSomethingElse();
    }

    public synchronized void doSomething() {
        System.out.println(this + " something sleepy!");
        try {
            Thread.sleep(2000);
            System.out.println(this + " woke up!");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class TestChild extends Test {
        @Override
        public void doSomething() {
            super.doSomething();
        }

        public synchronized void doSomethingElse() {
            System.out.println(this + " something else");
        }
    }

}
