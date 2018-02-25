package org.ray.leetcode;

import java.util.Arrays;

/**
 * created by ray
 * Date: 24/02/2018
 * Time: 23:51
 */
public class RemoveDuplicateElement {

    public static <T extends Comparable<? super T>> int removeDuplicate(T[] a) {
        if (a == null || a.length == 0) {
            return 0;
        }
        int current = 0;
        int length = a.length;

        for (int index = current + 1; index < a.length; index++) {
            if (a[index].compareTo(a[current]) == 0) {
                length--;
            } else {
                current = index;
            }
        }
        return length;
    }

    public static void main(String[] args) {
        Integer[] a = { 1, 1, 2, 2, 2, 2, 3, 4, 4, 4, 4 };
        System.out.println(
                "array detail:" + Arrays.toString(a) + "; array length: " + a.length + "; length: " +
                removeDuplicate(a));
    }
}
