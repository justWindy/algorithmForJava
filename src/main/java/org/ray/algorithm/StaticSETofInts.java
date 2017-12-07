package org.ray.algorithm;

import java.util.Arrays;

public class StaticSETofInts {

    private int[] a;

    public StaticSETofInts(int[] keys) {
        a = new int[keys.length];

        for (int i = 0; i < keys.length; i++) {
            a[i] = keys[i];
        }

        Arrays.sort(a);
        for (int i = 1; i < a.length; i++) {
            if (a[i] == a[i - 1]) {
                throw new IllegalArgumentException("Argument arrays contains duplicate keys.");
            }
        }
    }

    public int rank(int key) {
        int low = 0;
        int high = a.length - 1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (key < a[middle]) {
                high = middle - 1;
            } else if (key > a[middle]) {
                low = middle + 1;
            } else {
                return middle;
            }
        }

        return -1;
    }

    public boolean contains(int key) {
        return rank(key) != -1;
    }

}
