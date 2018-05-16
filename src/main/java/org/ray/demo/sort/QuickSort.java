package org.ray.demo.sort;

import org.ray.algorithm.StdOut;
import org.ray.algorithm.StdRandom;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

/**
 * created by helanzhou
 */
public class QuickSort {

    private QuickSort() {
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert isSorted(a);
    }

    private static void sort(Comparable[] a, int low, int high) {
        if (high <= low) {
            return;
        }
        int j = partition(a, low, high);
        sort(a, low, j - 1);
        sort(a, j + 1, high);
        assert isSorted(a, low, high);
    }

    private static int partition(Comparable[] a, int low, int high) {
        int i = low;
        int j = high + 1;

        Comparable v = a[low];
        while (true) {
            while (less(a[++i], v)) {
                if (i == high) {
                    break;
                }
            }

            while (less(v, a[--j])) {
                if (j == low) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }
            swap(a, i, j);
        }
        swap(a, low, j);

        return j;
    }

    public static Comparable select(Comparable[] a, int k) {
        if (k < 0 || k >= a.length) {
            throw new IndexOutOfBoundsException("Selected element out of bounds");
        }
        StdRandom.shuffle(a);
        int low = 0, high = a.length - 1;
        while (high > low) {
            int i = partition(a, low, high);
            if (i > k) {
                high = i - 1;
            } else if (i < k) {
                low = i + 1;
            } else {
                return a[i];
            }
        }

        return a[low];
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }

        return true;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void swap(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i]);
        }
    }

    public static void main(String[] args) {
        Integer[] array = { 9, 3, 8, 2, 10, 23, 50, 23, 11, 3, 10, 33 };

        System.out.println(Arrays.toString(array));
        /*sort(array);*/
        System.out.println(Arrays.toString(array));
    }
}
