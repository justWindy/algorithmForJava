package org.ray.leetcode.demo;

import java.util.Arrays;

/**
 * created by ray
 * Date: 2018/4/19
 * Time: 08:35
 */
public class RotateString {

    public static void main(String[] args) {
        String s = "I am a student.";
        char[] chars = s.toCharArray();

        leftRotateString(chars, s.length(), 7);

        System.out.println(Arrays.toString(chars));
        System.out.println(String.valueOf(chars));

        System.out.println("test help");

    }

    private static void reverseString(char[] s, int from, int to) {
        while (from < to) {
            char temp = s[from];
            s[from++] = s[to];
            s[to--] = temp;
        }
    }

    private static void leftRotateString(char[] s, int n, int m) {
        m %= n;
        //reverseString(s, 0, m - 1);
        reverseString(s, m, n - 1);
        reverseString(s, 0, n - 1);
    }

}
