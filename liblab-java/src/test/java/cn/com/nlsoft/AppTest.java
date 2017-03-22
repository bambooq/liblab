package cn.com.nlsoft;


import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void test() {
        StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
        for (StackTraceElement s : callStack) {
            System.out.println("s.getClassName() -> " + s.getClassName());
            System.out.println("s.getMethodName() -> " + s.getMethodName());
            System.out.println("s.getLineNumber() -> " + s.getLineNumber());
        }
    }

}
