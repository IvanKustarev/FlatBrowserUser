package User;

import CommonClasses.User;

public class Printer{
    public static <T> void print(T phrase){
        System.out.print(phrase);
    }
    public static <T> void println(T phrase){
        System.out.println(phrase);
    }
    public static <T> void printf(String phrase1, String phrase2, T parameter){
        System.out.printf(phrase1, phrase2, parameter);
    }
}
