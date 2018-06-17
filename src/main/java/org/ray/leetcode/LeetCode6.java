package org.ray.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * created by ray
 * Date: 2018/5/23
 * Time: 00:13
 */
public class LeetCode6 {

    public static void main(String[] args) {
        String s = "PAYPALISHIRING";
        String s2 = "PAYPALISHIRING";

        System.out.println(convert(s, 3));
        System.out.println(convert(s2, 4));
    }

    private static String convert(String s, int numRows) {
        if (s == null || s.length() < 1 || numRows <= 1) {
            return s;
        }

        List<List<Character>> list = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            list.add(new ArrayList<Character>());
        }

        int index = 0;
        int i = 0;
        int len = s.length();

        while (i < len) {
            while (index < numRows && i < len) {
                list.get(index).add(s.charAt(i));
                i++;
                index++;
            }

            if (index == numRows) {
                index -= 2;
            }

            while (index > 0 && i < len) {
                list.get(index).add(s.charAt(i));
                i++;
                index--;
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < numRows; j++) {
            for (int k=0; k<list.get(j).size(); k++) {
                builder.append(list.get(j).get(k));
            }
        }
        return builder.toString();
    }

}
