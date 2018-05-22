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

    /**
     * 遍历字符串S的每一个字串， 去判断这个字串是不是回文，是回文的话看看长度是不是比maxLength大。
     * 遍历每一个字串的方法要O(n^2)，判断每一个字串是不是回文的时间复杂度是O(n),所以暴力方法的的总时间
     * 复杂度是O(n^3)
     *
     * @param s 判断回文的字符串
     * @return 回文字符字串
     */
    private static String bruteForceSolution(String s) {
        //字符串长度
        int len = s.length();
        //最长回文字符串长度
        int maxLength = 0;
        //回文开始的地方
        int start = 0;

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                int index1 = 0;
                int index2 = 0;

                //对每个子串都从两边开始向中间遍历
                for (index1 = i, index2 = j; index1 < index2; index1++, index2--) {
                    if (s.charAt(index1) != s.charAt(index2)) {
                        break;
                    }
                }

                //  若index1=index2，表示字串类似于abcba这种类型，若大于，则是abccba这种类型
                if (index1 >= index2 && j - i > maxLength) {
                    maxLength = j - i + 1;
                    start = i;
                }
            }
        }

        if (maxLength > 0) {
            return s.substring(start, start + maxLength);
        }
        return "";
    }

    /**
     * 回文字符串的字串也是回文，比如P[i,j]（表示以i开始，以j结束的字串）是回文字串，那么P[i+1,j-1]也是回文字符串。这样最长回文
     * 字串就能分解成一系列子问题了。这样需要额外的空间O(N^2)，算法复杂度也是O(N^2).
     * <p>
     * 首先定义状态方程和转移方程：
     * P[i,j]=false:表示子串[i,j]不是回文串。P[i,j]=true：表示子串[i,j]是回文串
     * P[i,j]=true:当且仅当P[i+1,j-1]=true && (s[i]==s[j])
     * 否则p[i,j] =false;
     *
     * @param s
     * @return
     */
    private static String dynamicPlaningSolution(String s) {
        int len = s.length();
        int start = 0;
        int maxLength = 0;

        boolean[][] p = new boolean[len][len];

        //子串长度为1和2的初始化
        for (int i = 0; i < len; i++) {
            p[i][i] = true;
            if (i < len - 1 && s.charAt(i) == s.charAt(i + 1)) {
                p[i][i + 1] = true;
                start = i;
                maxLength = 2;
            }
        }

        // 使用上述结果可以dp出子串长度为3~len-1的子串
        for (int strLen = 3; strLen < len; strLen++) {
            for (int i = 0; i <= len - strLen; i++) {
                int j = i + strLen - 1; // 子串结束的位置
                if (p[i + 1][j - 1] && s.charAt(i) == s.charAt(j)) {
                    p[i][j] = true;
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
                j--;
                k++;
            }
        }

        if (maxLength > 0) {
            return s.substring(start, start + maxLength);
        }

        return null;
    }

    private static String ManacherSolution(String s) {
        if (s == null || s.length() < 1) {
            return null;
        }

        String str = dealWithString(s);
        int[] res = new int[str.length()];
        int R = 0;
        int C = 0;
        int maxC = 0;
        res[0] = 0;
        for (int i = 1; i < str.length(); i++) {
            int j = 2 * C - i;
            if (j >= 0 && res[j] < R - i) {
                res[i] = res[j];
            } else {
                int k = 1;
                while (R + k < str.length() && 2 * i - R - k >= 0) {
                    if (str.charAt(R + k) == str.charAt(2 * i - R - k)) {
                        k++;
                    } else {
                        break;
                    }
                }
                res[i] = R - i + k - 1;
                if (res[i] + i > R) {
                    R = res[i] + i;
                    C = i;
                }
            }

            maxC = res[maxC] > res[i] ? maxC : i;
        }

        String subStr = str.substring(maxC - res[maxC], maxC + res[maxC] + 1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < subStr.length(); i++) {
            if (subStr.charAt(i) != '#') {
                builder.append(subStr.charAt(i));
            }
        }

        return builder.toString();
    }

    private static String dealWithString(String s) {
        StringBuilder builder = new StringBuilder();
        builder.append("#");
        for (int i = 0; i < s.length(); i++) {
            builder.append(s.charAt(i));
            builder.append("#");
        }
        return builder.toString();
    }

}
