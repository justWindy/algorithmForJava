package org.ray.datastructure.sort;

/**
 * created by ray
 * Date: 25/02/2018
 * Time: 11:19
 */
public class RecursionInsertSort {

    public static <T extends Comparable<? super T>> void insertInOrder(T element, T[] a, int begin, int end) {
        if (element.compareTo(a[end]) >= 0) {
            a[end + 1] = element;
        } else if (begin < end) {
            a[end + 1] = a[end];
            insertInOrder(element, a, begin, end - 1);
        } else {
            a[end + 1] = a[end];
            a[end] = element;
        }
    }

    public static <T extends Comparable<? super T>> void insertionSort(T[] a, int first, int last) {
        if (first < last) {
            insertionSort(a, first, last - 1);
            insertInOrder(a[last], a, first, last - 1);
        }
    }

}
