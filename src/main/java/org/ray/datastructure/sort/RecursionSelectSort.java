package org.ray.datastructure.sort;

/**
 * created by ray
 * Date: 24/02/2018
 * Time: 23:37
 */
public class RecursionSelectSort {

    private static void swap(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static <T extends Comparable<? super T>> int minValue(T[] a, int start) {
        int min = start;

        for (int i = 1 + start; i < a.length; i++) {
            if (a[i].compareTo(a[min]) < 0) {
                min = i;
            }
        }

        return min;
    }

    public static <T extends Comparable<? super T>> void selectionSort(T[] a, int first, int last) {
        if (first < last) {
            int min = minValue(a, first);
            swap(a, first, min);
            selectionSort(a, first + 1, last);
        }
    }

}
