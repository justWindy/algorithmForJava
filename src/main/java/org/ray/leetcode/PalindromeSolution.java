package org.ray.leetcode;

/**
 * leetcode 9
 *
 * @author ray
 * created by ray
 * Date: 2018/4/21
 * Time: 16:04
 */
public class PalindromeSolution {

    public static void main(String[] args) {
        System.out.println(isPalindrome(121));
    }

    private static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        } else if (x == 0) {
            return true;
        }

        String value = String.valueOf(x);

        char[] chars = value.toCharArray();

        int length = chars.length;

        for (int i = 0; i < length / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[length - i - 1];
            chars[length - i - 1] = temp;
        }

        if (String.valueOf(x).equals(String.valueOf(chars))) {
            return true;
        }

        return false;

    }

}
