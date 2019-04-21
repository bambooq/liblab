package cn.com.nlsoft.responsibility;

public interface Interceptor {
    /**
     * 具体拦截处理
     * @param invocation
     * @return
     * @throws Exception
     */
    Object intercept(Invocation invocation) throws Exception;

    /**
     *  插入目标类
     * @param target
     * @return
     */
    Object plugin(Object target);
}
