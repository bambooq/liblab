package cn.com.nlsoft.responsibility;

public class TransactionInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Exception {
        System.out.println("开启事务");
        Object result = invocation.process();
        System.out.println("提交事务");
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return TargetProxy.wrap(target,this);
    }
}
