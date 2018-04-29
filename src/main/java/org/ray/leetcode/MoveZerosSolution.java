package org.ray.leetcode;

import java.util.Arrays;

/**
 * leetCode 283
 *
 * @author ray
 * created by ray
 * Date: 2018/4/29
 * Time: 17:05
 */
public class MoveZerosSolution {

    public static void main(String[] args) {
        int[] nums = { 0, 1, 0, 3, 12 };
        moveZeros(nums);
        System.out.println(Arrays.toString(nums));
    }

    private static void moveZeros(int[] nums) {
        if (nums == null) {
            return;
        }

        int length = nums.length;
        int position = 0;

        for (int i = 0; i < length; i++) {
            if (nums[i] == 0) {
                position = i;
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] != 0) {
                        nums[position] = nums[j];
                        position = j;
                    }
                }
                nums[position] = 0;
            }
        }
    }

}
