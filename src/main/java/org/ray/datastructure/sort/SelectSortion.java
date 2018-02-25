package org.ray.datastructure.sort;

/**
 * created by ray
 * Date: 24/02/2018
 * Time: 08:25
 */
public class SelectSortion {

    public static <T extends Comparable<? super T>> void selectionSort(T[] a, int n) {

        for (int index = 0; index < n - 1; index++) {
            int indexOfNextSmallest = getIndexOfSmallest(a, index, n - 1);
            if (index != indexOfNextSmallest) {
                swap(a, index, indexOfNextSmallest);
            }
        }
    }

    private static void swap(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static <T extends Comparable<? super T>> int getIndexOfSmallest(T[] a, int first, int last) {
        T min = a[first];
        int indexOfMin = first;

        for (int index = first + 1; index <= last; index++) {
            if (a[index].compareTo(min) < 0) {
                min = a[index];
                indexOfMin = index;
            }
        }

        return indexOfMin;
    }
}
