package org.ray.demo;

import org.ray.algorithm.StdIn;
import org.ray.algorithm.StdOut;
import org.ray.demo.standard.Bag;

/**
 * created by ray
 * Date: 25/03/2018
 * Time: 19:06
 */
public class Stats {

    public static void main(String[] args) {
        Bag<Double> numbers = new Bag<>();

        while (!StdIn.isEmpty()) {
            numbers.add(StdIn.readDouble());
        }

        int N = numbers.size();
        double sum = 0.0;

        for (double x : numbers) {
            sum += x;
        }

        double mean = sum / N;

        sum = 0.0;
        for (double x : numbers) {
            sum += (x - mean) * (x - mean);
        }

        double std = Math.sqrt(sum / (N - 1));

        StdOut.printf("Mean: %.2f\n", mean);
        StdOut.printf("Std dev: %.2f\n", std);
    }
}
