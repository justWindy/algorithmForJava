package org.ray.algorithm.collections;

import org.ray.algorithm.StdOut;

/**
 * created by ray
 * Date: 28/01/2018
 * Time: 19:35
 */
public class HeapSort {

    private HeapSort() {

    }

    public static void sort(Comparable[] pq) {
        int n = pq.length;
        for (int k = n / 2; k >= 1; k--) {
            sink(pq, k, n);
        }

        while (n > 1) {
            exchange(pq, 1, n--);
            sink(pq, 1, n);
        }
    }

    private static void sink(Comparable[] pq, int k, int n) {
        while (2 * k < n) {
            int j = 2 * k;
            if (j < n && less(pq, j, j + 1)) {
                j++;
            }
            if (!less(pq, k, j)) {
                break;
            }
            exchange(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }

    private static void exchange(Object[] pq, int i, int j) {
        Object swap = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = swap;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }

        return true;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
}
