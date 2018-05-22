package org.ray.leetcode;

/**
 * created by ray
 * Date: 2018/5/11
 * Time: 23:53
 */
public class LeetCode11 {

    public static void main(String[] args) {

    }

    private static int maxArea(int[] height) {

        int start = 0;
        int end = height.length - 1;
        int result = Integer.MIN_VALUE;

        while (start < end) {
            int area = Math.min(height[end], height[start]) * (end - start);

            result = Math.max(result, area);
            if (height[start] <= height[end]) {
                start++;
            } else {
                end--;
            }
        }

        return result;
    }

}
