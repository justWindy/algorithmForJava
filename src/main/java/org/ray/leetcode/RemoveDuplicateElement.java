package org.ray.leetcode;

import java.util.Arrays;

/**
 * leetCode 26
 *
 * @author lanzhou.hlz
 * created by ray
 * Date: 24/02/2018
 * Time: 23:51
 */
public class RemoveDuplicateElement {

    public static void main(String[] args) {
        int[] a = { 1, 1, 2, 2, 2, 2, 3, 4, 4, 4, 4 };

        int[] b = { 1 };

        System.out.println(removeDuplicates(a));
        System.out.println(Arrays.toString(a));

    }

    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int current = 0;
        int index = 1;
        int cycleNum = nums.length;

        for (; index < nums.length && cycleNum >= 0; ) {
            if (nums[index] == nums[current]) {
                for (int j = index + 1; j < nums.length; j++) {
                    nums[j - 1] = nums[j];
                }
            } else {
                current = index;
                index++;
            }
            cycleNum--;
        }
        return index;
    }
}
