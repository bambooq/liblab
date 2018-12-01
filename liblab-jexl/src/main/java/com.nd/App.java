package com.nd;

import org.apache.commons.jexl3.*;

public class App {


    public static void main(String[] args) {
        Person owner = new Person();
        owner.setAge(52);
        owner.setName("Clark");
        Cat cat = new Cat();
        cat.setAge(11);
        cat.setName("Tom");
        cat.setOwner(owner);

        // Create or retrieve an engine
        JexlEngine jexl = new JexlBuilder().create();

        // Create an expression
        String express = "cat.owner.name eq 'Clark' & cat.name eq '1Tom'";
        JexlExpression e = jexl.createExpression(express);

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("cat", cat);

        // Now evaluate the expression, getting the result
        Object o = e.evaluate(jc);
        System.out.println(o);
    }
}
