package org.ray.algorithm;

import org.ray.algorithm.search.BinarySearch;

import java.util.Arrays;

public class TwoSumFast {

    public static int count(int[] a) {
        Arrays.sort(a);

        int lenght = a.length;
        int count = 0;

        for (int i = 0; i < lenght; i++) {
            if (BinarySearch.rank(-a[i], a) > i) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int[] a = In.readInts(args[0]);
        StdOut.println(count(a));
    }

}
