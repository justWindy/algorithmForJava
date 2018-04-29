package org.ray.leetcode;

/**
 * leetcode 717
 *
 * @author ray
 * created by ray
 * Date: 2018/4/29
 * Time: 17:49
 */
public class OneBitCharacterSolution {

    public static void main(String[] args) {
        int[] test = {1, 1, 1, 0};
        System.out.println(isOneBitCharacter(test));
    }

    private static boolean isOneBitCharacter(int[] bits) {
        int length = bits.length;
        if (length == 1) {
            return true;
        }

        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(bits[i]);
        }

        String str = builder.toString();
        while (length > 1) {
            if (str.startsWith("11")) {
                builder.delete(0, 2);
            } else if (str.startsWith("10")) {
                builder.delete(0, 2);
            } else if (str.startsWith("0")) {
                builder.delete(0, 1);
            }
            str = builder.toString();
            length = builder.length();
        }

        if (length == 1) {
            return true;
        }

        return false;
    }

}
