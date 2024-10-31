package org.topcard;
/**
 * Main entry point
 * <p>
 * Author: Rajesh Rajchal
 * Date: 10/27/2024
 * Subject: MSSE 670 Object Oriented Software construction
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        int a = 1;
        int b = 1;
        if (a == b) {
            System.out.println("a and b are equal");
        }


        String name1 = new String("MSSE370");
        String name2 = name1;

        if (name1 == name2) {
            System.out.println("Name1 and Name2 are equal");
        }

    }
}