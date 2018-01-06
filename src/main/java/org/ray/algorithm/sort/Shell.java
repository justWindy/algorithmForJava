package org.ray.algorithm.sort;

import org.ray.algorithm.StdOut;

/**
 * {@Code Shell} 类实现了一个使用希尔算法的静态方法来对一个数组进行排序,
 * <p>
 * 可以使用高纳德的递增序列 (1, 4, 13, 40, ...)
 *
 * @author ray
 * created by ray
 * Date: 06/01/2018
 * Time: 13:51
 */
public class Shell {

    private Shell() {

    }

    public static void sort(Comparable[] a) {
        int size = a.length;

        int h = 1;
        while (h < size / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < size; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exchange(a, j, j - h);
                }
            }
            assert isHsorted(a, h);
            h /= 3;
        }
        assert isSorted(a);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
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

    private static boolean isHsorted(Comparable[] a, int h) {
        for (int i = h; i < a.length; i++) {
            if (less(a[i], a[i - h])) {
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
