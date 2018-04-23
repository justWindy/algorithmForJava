package org.ray.leetcode;

/**
 * leetcode 14
 *
 * @author ray
 * created by ray
 * Date: 2018/4/24
 * Time: 00:13
 */
public class LongestCommonPrefixSolution {

    public static void main(String[] args) {
        String[] test = { "flower", "flow", "flight" };

        System.out.println(longestCommonPrefix(test));

        String[] test2 = { "dog", "racecar", "car" };
        System.out.println(longestCommonPrefix(test2));
    }

    private static String longestCommonPrefix(String[] strs) {
        String commonPrefix = strs[0];
        boolean contain = false;
        for (int i = commonPrefix.length(); i >= 0; i--) {
            String temp = commonPrefix.substring(0, i);
            for (String str : strs) {
                if (!str.startsWith(temp)) {
                    contain = false;
                    break;
                }
                contain = true;
            }

            if (contain) {
                return temp;
            }
        }

        return "";
    }

}
