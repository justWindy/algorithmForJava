package org.ray.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * created by ray
 * Date: 2018/4/21
 * Time: 16:26
 */
public class LongestSubStringSolution {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("pabcdaw"));
    }

    private static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) {
            return 0;
        }

        if (s.length() == 1) {
            return 1;
        }
        Map<Character, Integer> map = new HashMap<Character, Integer>();

        int index = 0;
        int maxLength = 0;
        char[] chars = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {

            if (map.keySet().contains(chars[i])) {
                if (index <= map.get(chars[i])) {
                    index = map.get(chars[i]) + 1;
                }
            }

            if (i - index >= maxLength) {
                maxLength = i + 1 - index;
            }

            map.put(chars[i], i);

        }

        return maxLength;
    }

}
