package a;

import java.util.Random;

public class App {

    public static void main(String[] args) {
        int random1 = (int) (Math.random() * 10);
        int random2 = (int) (Math.random() * 10 + 10);
        int random3 = (int) (Math.random() * 50);
        System.out.println(random1);
        System.out.println(random2);
        System.out.println(random3);
        System.out.println(new Random().nextInt(50));
        System.out.println(Math.random());
    }
}
