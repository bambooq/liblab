package cn.com.nlsoft.responsibility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InterceptorChain {

    private List<Interceptor> interceptorList = new ArrayList<>();

    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptorList) {
            target = interceptor.plugin(target);
        }
        return target;
    }

    public void addInterceptor(Interceptor interceptor) {
        interceptorList.add(interceptor);
    }

    /**
     * 返回一个不可修改集合，只能通过addInterceptor方法添加
     * 这样控制权就在自己手里
     * @return
     */
    public List<Interceptor> getInterceptorList() {
        return Collections.unmodifiableList(interceptorList);
    }

}
