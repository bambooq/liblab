package cn.com.nlsoft.responsibility;

/**
 * @author Administrator
 */
public class TargetImpl implements Target {

    @Override
    public String execute(String name) {
        System.out.println("execute() "+ name);
        return name;
    }
}
