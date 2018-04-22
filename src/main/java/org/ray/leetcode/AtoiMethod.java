package org.ray.leetcode;

/**
 * leetCode 8
 *
 * @author ray
 * created by ray
 * Date: 2018/4/21
 * Time: 14:40
 */
public class AtoiMethod {

    public static void main(String[] args) {
        System.out.println(myAtoi("-2147483648"));
    }

    private static int myAtoi(String str) {
        String current = str.trim();
        boolean positiveFlag = false;
        char[] chars = null;
        if (current.startsWith("-")) {
            positiveFlag = true;
            chars = current.substring(1).toCharArray();
        } else if (current.startsWith("+")) {
            chars = current.substring(1).toCharArray();
        } else {
            chars = current.toCharArray();
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isDigit(chars[i])) {
                builder.append(chars[i]);
            } else {
                break;
            }
        }

        if (builder.length() == 0) {
            return 0;
        } else if (builder.length() > String.valueOf(Integer.MAX_VALUE).length()) {
            if (positiveFlag) {
                return Integer.MIN_VALUE;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        long longValue = Long.valueOf(builder.toString());
        if ((longValue - 1) >= Integer.MAX_VALUE) {
            if (positiveFlag) {
                return Integer.MIN_VALUE;
            }
        }

        if (longValue > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }

        if (positiveFlag) {
            return -(int) longValue;
        }

        return (int) longValue;

    }

}
