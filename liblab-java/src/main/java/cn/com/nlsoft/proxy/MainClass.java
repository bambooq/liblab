package cn.com.nlsoft.proxy;

public class MainClass {
    public static void main(String[] args) {
        HelloService proxy = (HelloService) HelloServiceProxy.buildProxy(new HelloServiceImpl());
        proxy.say("Zheng Qi");
    }
}
