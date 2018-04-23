package org.ray.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * leetcode 13
 *
 * @author ray
 * created by ray
 * Date: 2018/4/24
 * Time: 00:52
 */
public class RomanToIntSolution {

    public static void main(String[] args) {
        System.out.println(romanToInt("MCMXCIV"));
    }

    private static int romanToInt(String s) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        map.put("IV", 4);
        map.put("IX", 9);
        map.put("XL", 40);
        map.put("XC", 90);
        map.put("CD", 400);
        map.put("CM", 900);

        char[] chars = s.toCharArray();

        int sum = 0;

        for (int i = 0; i < s.length(); ) {
            if (i + 1 < s.length()) {
                String temp = String.valueOf(chars[i]) + String.valueOf(chars[i + 1]);
                if (map.get(temp) != null) {
                    sum += map.get(temp);
                    i += 2;
                } else {
                    sum += map.get(String.valueOf(chars[i]));
                    i++;
                }
            } else {
                sum += map.get(String.valueOf(chars[i]));
                i++;
            }
        }

        return sum;
    }

}
