package org.ray.leetcode;

import java.util.Arrays;

/**
 * leetcode 27
 *
 * @author ray
 * created by ray
 * Date: 2018/4/25
 * Time: 08:29
 */
public class RemoveElementSolution {

    public static void main(String[] args) {
        int[] nums = { 0, 1, 2, 2, 3, 0, 4, 2 };

        System.out.println(removeElement(nums, 2));

        System.out.println(Arrays.toString(nums));
    }

    private static int removeElement(int[] nums, int val) {
        if (nums == null) {
            return 0;
        }

        int length = nums.length;

        for (int i = 0; i < length; i++) {
            if (nums[i] == val) {
                for (int j = i; j < nums.length - 1; j++) {
                    nums[j] = nums[j + 1];
                }
                length--;
                i--;
            }
        }

        return length;
    }

}
