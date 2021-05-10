package cn.com.nlsoft.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HelloServiceProxy implements InvocationHandler {

    private Object target;

    public HelloServiceProxy(Object target) {
        this.target = target;
    }

    public static Object buildProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new HelloServiceProxy(target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("ready to say hello");
        Object result = method.invoke(target, args);
        System.out.println("said hello");
        return result;
    }
}
