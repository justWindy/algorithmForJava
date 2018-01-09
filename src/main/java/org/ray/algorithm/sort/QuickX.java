package org.ray.algorithm.sort;

import org.ray.algorithm.StdOut;

/**
 * created by helanzhou
 * Date: 1/9/18
 * Time: 11:29 AM
 */
public class QuickX {

    private static final int INSERTION_SORT_CUTOFF = 8;
    private static final int MEDIAN_OF_3_CUTOFF    = 40;

    private QuickX() {

    }

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int low, int high) {
        int n = high - low + 1;

        if (n <= INSERTION_SORT_CUTOFF) {
            insertionSort(a, low, high);
            return;
        } else if (n <= MEDIAN_OF_3_CUTOFF) {
            int m = median3(a, low, low + n / 2, high);
            exchange(a, m, low);
        } else {
            int eps = n / 8;
            int mid = low + n / 2;
            int m1 = median3(a, low, low + eps, low + eps + eps);
            int m2 = median3(a, mid - eps, mid, mid + eps);
            int m3 = median3(a, high - eps - eps, high - eps, high);
            int ninther = median3(a, m1, m2, m3);
            exchange(a, ninther, low);
        }

        int i = low, j = high + 1;
        int p = low, q = high + 1;
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

            if (i == j && eq(a[i], v)) {
                exchange(a, ++p, i);
            }

            if (i >= j) {
                break;
            }

            exchange(a, i, j);
            if (eq(a[i], v)) {
                exchange(a, ++p, i);
            }
            if (eq(a[j], v)) {
                exchange(a, --q, j);
            }
        }

        i = j + 1;
        for (int k = low; k <= p; k++) {
            exchange(a, k, j--);
        }
        for (int k = high; k >= q; k--) {
            exchange(a, k, i++);
        }

        sort(a, low, j);
        sort(a, i, high);
    }

    private static void insertionSort(Comparable[] a, int low, int high) {
        for (int i = low; i <= high; i++) {
            for (int j = i; j > low && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
    }

    private static int median3(Comparable[] a, int i, int j, int k) {
        return (less(a[i], a[j]) ?
                (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
                (less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean eq(Comparable v, Comparable w) {
        return v.compareTo(w) == 0;
    }

    private static void exchange(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
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
