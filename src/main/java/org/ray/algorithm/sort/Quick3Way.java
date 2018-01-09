package org.ray.algorithm.sort;

import org.ray.algorithm.StdOut;
import org.ray.algorithm.StdRandom;

/**
 * created by helanzhou
 * Date: 1/9/18
 * Time: 11:28 AM
 */
public class Quick3Way {

    private Quick3Way() {

    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    public static void sort(Comparable[] a, int low, int high) {
        if (high <= low) {
            return;
        }
        int lt = low, gt = high;
        Comparable v = a[low];
        int i = low;

        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) {
                exchange(a, lt++, i++);
            } else if (cmp > 0) {
                exchange(a, i, gt--);
            } else {
                i++;
            }
        }

        sort(a, low, lt - 1);
        sort(a, gt + 1, high);
        assert isSorted(a, low, high);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exchange(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }

        return true;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
}
