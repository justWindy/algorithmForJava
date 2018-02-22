package org.ray.chuanda;

/**
 * created by ray
 * Date: 22/02/2018
 * Time: 16:39
 */
public class BinarySearch<T extends Comparable<T>> {

    private int search(T[] list, T key, int n) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (key.compareTo(list[mid]) == 0) {
                return mid;
            }
            if (key.compareTo(list[mid]) > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

}
