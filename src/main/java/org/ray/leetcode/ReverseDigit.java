package org.ray.leetcode;

/**
 * created by ray
 * Date: 2018/4/20
 * Time: 21:56
 */
public class ReverseDigit {

    public static void main(String[] args) {

        System.out.println(Integer.MIN_VALUE);

    }

    private static int reverse(int x) {
        if (x == 0) {
            return 0;
        }
        boolean positiveFlag = false;
        if (x < 0) {
            x = -x;
            positiveFlag = true;
        }

        String str = String.valueOf(x);
        char[] chars = null;
        if (str.endsWith("0")) {
            chars = str.substring(0, str.length() - 1).toCharArray();
        } else {
            chars = str.toCharArray();
        }
        int length = chars.length - 1;
        for (int i = 0; i <= length / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[length - i];
            chars[length - i] = temp;
        }
        String current = String.valueOf(chars);
        if (current.length() > 10) {
            return 0;
        }
        long longValue = Long.parseLong(current);
        if (longValue > Integer.MAX_VALUE) {
            return 0;
        }
        if (positiveFlag) {
            if (longValue - 1 > Integer.MAX_VALUE) {
                return 0;
            } else if ((int) (longValue - 1) == Integer.MAX_VALUE) {
                return Integer.MIN_VALUE;
            }
            return -Integer.valueOf(current);
        }
        return Integer.valueOf(current);
    }

}
