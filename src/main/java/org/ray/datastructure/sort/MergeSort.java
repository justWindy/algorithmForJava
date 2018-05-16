package org.ray.datastructure.sort;

import java.util.Arrays;

/**
 * created by ray
 * Date: 25/02/2018
 * Time: 19:14
 */
public class MergeSort {

    public static <T extends Comparable<? super T>> void mergeSort(T[] a, int first, int last) {
        T[] tempArray = (T[]) new Comparable<?>[a.length];
        mergeSort(a, tempArray, first, last);

    }

    private static <T extends Comparable<? super T>> void mergeSort(T[] a, T[] tempArray, int first, int last) {
        if (first < last) {
            int mid = (first + last) / 2;
            mergeSort(a, first, mid);
            mergeSort(a, mid + 1, last);
            merge(a, tempArray, first, mid, last);
        }
    }

    private static <T extends Comparable<? super T>> void merge(T[] a, T[] tempArray, int first, int mid, int last) {
        for (int i = first; i <= last; i++) {
            tempArray[i] = a[i];
        }

        int i = first;
        int j = mid + 1;
        for (int k = first; k <= last; k++) {
            if (i > mid) {
                a[k] = tempArray[j++];
            } else if (j > last) {
                a[k] = tempArray[i++];
            } else if (tempArray[i].compareTo(tempArray[j]) < 0) {
                a[k] = tempArray[i++];
            } else {
                a[k] = tempArray[j++];
            }
        }
    }

    public static void main(String[] args) {
        Integer[] array = { 9, 6, 2, 4, 8, 7, 5, 3 };

        System.out.println("before sort:" + Arrays.toString(array));
        mergeSort(array, 0, array.length - 1);
        System.out.println("after sort:" + Arrays.toString(array));
    }

}
