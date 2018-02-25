package org.ray.datastructure.sort;

/**
 * created by ray
 * Date: 24/02/2018
 * Time: 23:02
 */
public class InsertionSort {

    public static <T extends Comparable<? super T>> void insertionSort(T[] a, int first, int last) {
        for (int unsorted = first + 1; unsorted < last; unsorted++) {
            T firstUnsorted = a[unsorted];
            insertInOrder(firstUnsorted, a, first, unsorted - 1);
        }
    }

    private static <T extends Comparable<? super T>> void insertInOrder(T element, T[] a, int begin, int end) {
        int index = end;
        while ((index >= begin) && (element.compareTo(a[index]) < 0)) {
            a[index + 1] = a[index];
            index--;
        }
        a[index + 1] = element;
    }

}
