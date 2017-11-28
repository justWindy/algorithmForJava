package org.ray.algorithm.math;

import org.ray.algorithm.StdDraw;
import org.ray.algorithm.StdRandom;

import java.util.Arrays;

public class GeometricUtils {

    public static void mathFunction() {
        int n = 100;
        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, n * n);
        StdDraw.setPenRadius(.01);
        for (int i = 1; i <= n; i++) {
            StdDraw.point(i, i);
            StdDraw.point(i, i * i);
            StdDraw.point(i, i * Math.log(i));
        }
    }

    public static void randomArray() {
        int n = 50;
        double[] a = new double[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniform();
        }

        for (int i = 0; i < n; i++) {
            double x = 1.0 * i / n;
            double y = a[i] / 2.0;
            double rw = 0.5 / n;
            double rh = a[i] / 2.0;
            StdDraw.filledRectangle(x, y, rw, rh);
        }
    }

    public static void sortedArray() {
        int size = 50;
        double[] a = new double[size];
        for (int i = 0; i < size; i++) {
            a[i] = StdRandom.uniform();
        }

        Arrays.sort(a);
        for (int i = 0; i < size; i++) {
            double x = 1.0 * i / size;
            double y = a[i] / 2.0;
            double rw = 0.5 / size;
            double rh = a[i] / 2.0;
            StdDraw.filledRectangle(x, y, rw, rh);
        }
    }

    public static void main(String[] args) {
        sortedArray();
    }

}
