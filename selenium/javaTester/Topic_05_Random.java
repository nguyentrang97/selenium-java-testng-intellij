package javaTester;

import java.util.Random;

public class Topic_05_Random {

    public static void main(String[] args){
        //ulities = tiện ích
        //Data Type: Class/Interface/Collection/String/Fload/Int/.....
        
        Random ran = new Random();
        System.out.println(ran.nextFloat());
        System.out.println(ran.nextDouble());
        System.out.println(ran.nextInt());
        System.out.println(ran.nextInt(99999));
        System.out.println(ran.nextInt(999));
        System.out.println("Automation" + ran.nextInt(9999) + "@gmail.com");
        System.out.println(ran.nextLong());
        System.out.println(ran.nextBoolean());
    }


}
