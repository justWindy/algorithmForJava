package org.ray.leetcode.demo;

/**
 * created by ray
 * Date: 2018/4/20
 * Time: 08:24
 */
public class CalAllPermutation {

    public static void main(String[] args) {
        String test = "a,b,c";
        permutation(test.split(","), 0,  2);
    }

    private static void permutation(String[] perm, int from, int to) {
        if (to <= 1) {
            return;
        }

        if (from == to) {
            for (String s : perm) {
                System.out.print(s);
            }

            System.out.println();
        } else {
            for (int j = from; j <= to; j++) {
                swap(perm, j, from);
                permutation(perm, from + 1, to);
                swap(perm, j, from);
            }
        }
    }

    private static void swap(String[] perm, int a, int b) {
        String temp = perm[a];
        perm[a] = perm[b];
        perm[b] = temp;
    }

}
