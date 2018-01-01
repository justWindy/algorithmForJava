package org.ray.algorithm;

import org.ray.algorithm.search.BinarySearch;

import java.util.Arrays;

public class ThreeSumFast {

    public static int count(int[] a) {
        Arrays.sort(a);
        int size = a.length;
        int count = 0;

        for (int i = 0; i < size; i++) {
            for (int j = i; j < size; j++) {
                if (BinarySearch.rank(-a[i] - a[j], a) > i) {
                    count++;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int[] a = In.readInts(args[0]);
        StdOut.println(count(a));
    }

}
