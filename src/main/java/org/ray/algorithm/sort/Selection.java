package org.ray.algorithm.sort;

import org.ray.algorithm.StdOut;

import java.util.Comparator;

/**
 * {@code Selection} 类提供静态方法, 可以使用选择排序来将
 * 指定数组排序
 *
 * @author lanzhou.hlz
 * created by helanzhou
 * Date: 1/5/18
 * Time: 7:11 PM
 */
public class Selection {

    private Selection() {

    }

    public static void sort(Comparable[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (less(array[j], array[min])) {
                    min = j;
                }
            }
            exchange(array, i, min);
            assert isSorted(array, 0, i);
        }
        assert isSorted(array);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean less(Comparator comparator, Object v, Object w) {
        return comparator.compare(v, w) < 0;
    }

    private static void exchange(Object[] array, int i, int j) {
        Object swap = array[i];
        array[i] = array[j];
        array[j] = swap;
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

    private static boolean isSorted(Object[] array, Comparator comparator) {
        return isSorted(array, comparator, 0, array.length);
    }

    private static boolean isSorted(Object[] array, Comparator comparator, int low, int high) {
        for (int i = low + 1; i < high; i++) {
            if (less(comparator, array[i], array[i - 1])) {
                return false;
            }
        }
        return true;
    }

    private static void show(Comparable[] array) {
        for (int i = 0; i < array.length; i++) {
            StdOut.println(array[i]);
        }
    }
}
