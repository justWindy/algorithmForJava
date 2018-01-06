package org.ray.algorithm.sort;

import java.util.Comparator;

/**
 * 这个 {@Code Insertion} 类, 提供静态方法, 能够利用插入排序算法来进行排序
 * <p>
 * <p>
 * 这个算法的最差情况是需要进行~1/2 n^2次的比较和交换, 所以, 这个算法不适合
 * 对大的数组进行排序.
 * 更准确的说, 该排序算法的交换次数差不多和数字(或者排序关键字)的倒序的数量差不多.
 * 因此, 该算法的复杂度, 对于一个部分有序的数组来说, 是一个线性增长的时间
 * <p>
 * 这个算法是一个稳定的, 并且只占用O(1)额外内存空间的算法
 * <p>
 *
 * @author ray
 * created by ray
 * Date: 06/01/2018
 * Time: 10:46
 */
public class Insertion {

    private Insertion() {

    }

    public static void sort(Comparable[] a) {
        int size = a.length;
        for (int i = 0; i < size; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }

    public static void sort(Comparable[] a, int low, int high) {
        for (int i = low; i < high; i++) {
            for (int j = i; j > low && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
        assert isSorted(a, low, high);
    }

    public static void sort(Object[] a, Comparator comparator) {
        int size = a.length;
        for (int i = 0; i < size; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1], comparator); j--) {
                exchange(a, j, j - 1);
            }
            assert isSorted(a, 0, i, comparator);
        }
        assert isSorted(a, comparator);
    }

    public static void sort(Object[] a, int low, int high, Comparator comparator) {
        for (int i = low; i < high; i++) {
            for (int j = i; j > low && less(a[j], a[j - 1], comparator); j--) {
                exchange(a, j, j - 1);
            }

        }
        assert isSorted(a, low, high, comparator);
    }

    public static int[] indexSort(Comparable[] a) {
        int size = a.length;
        int[] index = new int[size];
        for (int i = 0; i < size; i++) {
            index[i] = i;
        }

        for (int i = 0; i < size; i++) {
            for (int j = i; j > 0 && less(a[index[j]], a[index[j - 1]]); j--) {
                exchange(index, j, j - 1);
            }

        }

        return index;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean less(Object v, Object w, Comparator comparator) {
        return comparator.compare(v, w) < 0;
    }

    private static void exchange(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static void exchange(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] array) {
        return isSorted(array, 0, array.length);
    }

    private static boolean isSorted(Comparable[] array, int low, int high) {
        for (int i = low + 1; i < high; i++) {
            if (less(array[i], array[i - 1])) {
                return false;
            }

        }
        return true;
    }

    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, 0, a.length, comparator);
    }

    private static boolean isSorted(Object[] a, int low, int high, Comparator comparator) {
        for (int i = low + 1; i < high; i++) {
            if (less(a[i], a[i - 1], comparator)) {
                return false;
            }
        }

        return true;
    }
}
