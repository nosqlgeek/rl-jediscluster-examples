package org.nosqlgeek.demo;

/**
 * No need for JUnit as we do just demonstrate some stuff
 */
public class Test {

    public static void assertEquals(Object expected, Object provided) {

        if (!expected.equals(provided)) throw new RuntimeException("Expected " + expected + " but got " + provided + "!");

    }

}
