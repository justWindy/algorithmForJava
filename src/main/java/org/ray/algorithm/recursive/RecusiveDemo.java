package org.ray.algorithm.recursive;

import org.ray.algorithm.StdOut;

/**
 * created by ray
 * Date: 21/02/2018
 * Time: 22:32
 */
public class RecusiveDemo {

    public static void main(String[] args) {
        printOut(75623);
    }

    public static void printOut(int n) {
        if (n >= 10) {
            printOut(n / 10);
        }
        printDigit(n % 10);
    }

    public static void printDigit(int n) {
        StdOut.print(n);
    }
}
