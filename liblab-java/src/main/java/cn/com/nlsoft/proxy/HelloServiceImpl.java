package cn.com.nlsoft.proxy;

public class HelloServiceImpl implements HelloService {

    @Override
    public void say(String name) {
        System.out.println("hello " + name);
    }
}
