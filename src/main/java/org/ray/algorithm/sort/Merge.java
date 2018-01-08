package org.ray.algorithm.sort;

import org.ray.algorithm.StdOut;
import org.ray.algorithm.StdRandom;

/**
 * created by ray
 * Date: 07/01/2018
 * Time: 09:51
 */
public class Merge {

    private Merge() {

    }

    private static void merge(Comparable[] a, Comparable[] aux, int low, int middle, int high) {
        assert isSorted(a, low, middle);
        assert isSorted(a, middle + 1, high);

        for (int k = low; k < high; k++) {
            aux[k] = a[k];
        }

        int i = low, j = middle + 1;
        for (int k = low; k < high; k++) {
            if (i > middle) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }

        assert isSorted(a, low, high);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int low, int high) {
        if (high <= low) {
            return;
        }
        int mid = low + (high - low) / 2;
        sort(a, aux, low, mid);
        sort(a, aux, mid + 1, high);
        merge(a, aux, low, mid, high);
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length);
        assert isSorted(a);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length);
    }

    private static boolean isSorted(Comparable[] a, int low, int high) {
        for (int i = low + 1; i < high; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private static void merge(Comparable[] a, int[] index, int[] aux, int low, int mid, int high) {
        for (int k = low; k < high; k++) {
            aux[k] = index[k];
        }

        int i = low, j = mid + 1;
        for (int k = low; k < high; k++) {
            if (i > mid) {
                index[k] = aux[j++];
            } else if (j > high) {
                index[k] = aux[i++];
            } else if (less(a[aux[j]], a[aux[i]])) {
                index[k] = aux[j++];
            } else {
                index[k] = aux[i++];
            }
        }
    }

    private static void sort(Comparable[] a, int[] index, int[] aux, int low, int high) {
        if (high <= low) {
            return;
        }

        int mid = low + (high - low) / 2;
        sort(a, index, aux, low, mid);
        sort(a, index, aux, mid + 1, high);
        merge(a, index, aux, low, mid, high);
    }

    public static int[] indexSort(Comparable[] a) {
        int n = a.length;
        int[] index = new int[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        int[] aux = new int[n];
        sort(a, index, aux, 0, n - 1);
        return index;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    public static void main(String[] args) {
        Integer[] test = new Integer[15];
        for (int i = 0; i < test.length; i++) {
            test[i] = StdRandom.uniform(100);
        }

        show(test);
        sort(test);
        show(test);
    }
}
