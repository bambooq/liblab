package cn.com.nlsoft.responsibility;

public class MainClass {

    public static void main(String[] args) {
        Interceptor transactionInterceptor = new TransactionInterceptor();

        InterceptorChain interceptorChain = new InterceptorChain();
        interceptorChain.addInterceptor(transactionInterceptor);

        Target proxy = (Target) interceptorChain.pluginAll(new TargetImpl());

        proxy.execute("hello world");
    }
}
