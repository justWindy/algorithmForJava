package org.ray.leetcode;

/**
 * leetcode 5
 *
 * @author ray
 * created by ray
 * Date: 2018/4/22
 * Time: 19:56
 */
public class LongestPalindromeSolution {

    public static void main(String[] args) {

    }

    public static String longestPalindrome(String s) {

        return null;
    }

    private static String bruteForceSolution(String s) {
        int length = s.length();
        int maxLength = 0;
        int start = 0;

        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                int left = 0;
                int right = 0;
                for (left = i, right = j; left < right; left++, right--) {
                    if (s.charAt(left) != s.charAt(right)) {
                        break;
                    }
                }
                if (left >= right && j - i > maxLength) {
                    maxLength = j - i + 1;
                    start = i;
                }
            }
        }

        if (maxLength > 0) {
            return s.substring(start, start + maxLength);
        }

        return null;
    }

    private static String dynamicPlanningSolution(String s) {
        if (s == null) {
            return "";
        }
        int length = s.length();
        int maxLength = 0;
        int start = 0;

        boolean[][] judges = new boolean[length][length];
        for (int i = 0; i < length; i++) {
            judges[i][i] = true;

            if (i < length - 1 && s.charAt(i) == s.charAt(i + 1)) {
                judges[i][i + 1] = true;
                maxLength = 2;
                start = i;
            }
        }

        for (int strLen = 3; strLen <= length; strLen++) {
            for (int i = 0; i <= length - strLen; i++) {
                int j = i + strLen - 1;
                if (judges[i + 1][j - 1] && s.charAt(i) == s.charAt(j)) {
                    judges[i][j] = true;
                    maxLength = strLen;
                    start = i;
                }
            }
        }

        if (maxLength > 0) {
            return s.substring(start, start + maxLength);
        }

        return null;
    }

    private static String centerExtendSolution(String s) {
        if (s == null) {
            return "";
        }

        int len = s.length();
        int maxLength = 0;
        int start = 0;

        for (int i = 0; i < len; i++) {
            int j = i - 1;
            int k = i + 1;
            while (j >= 0 && k < len && s.charAt(j) == s.charAt(k)) {
                if (k - j + 1 > maxLength) {
                    maxLength = k - j + 1;
                    start = j;
                }
                j--;
                k++;
            }
        }

        for (int i = 0; i < len; i++) {
            int j = i;
            int k = i + 1;
            while (j >= 0 && k < len && s.charAt(j) == s.charAt(k)) {
                if (k - j + 1 > maxLength) {
                    maxLength = k - j + 1;
                    start = j;
                }
                k++;
                j--;
            }
        }

        if (maxLength > 0) {
            return s.substring(start, start + maxLength);
        }

        return null;
    }

    private static String manacheSolution() {
        return null;
    }

}
