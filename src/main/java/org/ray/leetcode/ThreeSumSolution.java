package org.ray.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * leetCode 15
 *
 * @author ray
 * created by ray
 * Date: 2018/4/24
 * Time: 23:20
 */
public class ThreeSumSolution {

    public static void main(String[] args) {
        int[] nums = { -2, 0, 1, 1, 2 };

        int[] num1 = { -1, -2, -3, 4, 1, 3, 0, 3, -2, 1, -2, 2, -1, 1, -5, 4, -3 };

        int[] num2 = { -3, -4, -2, 0, 2, 2, -2, 1, -1, 2, 3, -1, -5, -4, -5, 1 };

        int[] num3 = { -1, -2, -3, 4, 1, 3, 0, 3, -2, 1, -2, 2, -1, 1, -5, 4, -3 };
        System.out.println(threeSum(num3));
    }

    private static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> lists = new ArrayList<List<Integer>>();
        Map<Integer, Integer> negative = new HashMap<>();
        List<Integer> zero = new ArrayList<>();
        Map<Integer, Integer> positive = new HashMap<>();

        for (int value : nums) {
            if (value < 0) {
                if (negative.keySet().contains(value)) {
                    negative.put(value, negative.get(value) + 1);
                } else {
                    negative.put(value, 1);
                }
            } else if (value == 0) {
                zero.add(value);
            } else {
                if (positive.keySet().contains(value)) {
                    positive.put(value, positive.get(value) + 1);
                } else {
                    positive.put(value, 1);
                }
            }
        }

        if (zero.size() >= 3) {
            List<Integer> list = new ArrayList<>(3);
            list.add(0);
            list.add(0);
            list.add(0);
            lists.add(list);
        }

        Map<Integer, List<Integer>> added = new HashMap<>();

        for (Integer integer : negative.keySet()) {
            int positiveValue = -integer;
            for (Integer one : positive.keySet()) {
                if (positiveValue > one) {
                    int remain = positiveValue - one;
                    if (remain == one) {
                        int value = positive.get(one);
                        if (value >= 2) {
                            List<Integer> list = new ArrayList<>();
                            list.add(integer);
                            list.add(remain);
                            list.add(remain);
                            lists.add(list);
                        }
                    } else {
                        if (added.get(integer) != null) {
                            List<Integer> addedList = added.get(integer);
                            if (addedList.contains(one)) {
                                continue;
                            }
                        }
                        if (positive.get(remain) != null) {
                            if (added.get(integer) != null) {
                                List<Integer> tempList= added.get(integer);
                                tempList.add(remain);
                                added.put(integer, tempList);
                            } else {
                                List<Integer> newList = new ArrayList<>();
                                newList.add(remain);
                                added.put(integer, newList);
                            }
                            List<Integer> list = new ArrayList<>();
                            list.add(integer);
                            list.add(one);
                            list.add(remain);
                            lists.add(list);
                        }
                    }
                }
            }
            if (!zero.isEmpty()) {
                int positiveKey = -integer;
                if (positive.get(positiveKey) != null) {
                    List<Integer> list = new ArrayList<>();
                    list.add(integer);
                    list.add(0);
                    list.add(positiveKey);
                    lists.add(list);
                }
            }
        }

        added.clear();
        for (Integer key : positive.keySet()) {
            int negativeKey = -key;
            for (Integer one : negative.keySet()) {
                if (negativeKey < one) {
                    int remain = negativeKey - one;
                    if (remain == one) {
                        int value = negative.get(one);
                        if (value >= 2) {
                            List<Integer> list = new ArrayList<>();
                            list.add(remain);
                            list.add(key);
                            list.add(remain);
                            lists.add(list);
                        }
                    } else {
                        if (added.get(key) != null ) {
                            List<Integer> addedList = added.get(key);
                            if (addedList.contains(one)) {
                                continue;
                            }
                        }
                        if (negative.get(remain) != null) {
                            if (added.get(key) != null) {
                                List<Integer> tempList= added.get(key);
                                tempList.add(remain);
                                added.put(key, tempList);
                            } else {
                                List<Integer> newList = new ArrayList<>();
                                newList.add(remain);
                                added.put(key, newList);
                            }
                            List<Integer> list = new ArrayList<>();
                            list.add(key);
                            list.add(one);
                            list.add(remain);
                            lists.add(list);
                        }
                    }
                }
            }
        }

        return lists;

    }
}
