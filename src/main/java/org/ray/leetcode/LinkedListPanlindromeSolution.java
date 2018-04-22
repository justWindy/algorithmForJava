package org.ray.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * leetcode 234
 *
 * @author ray
 * created by ray
 * Date: 2018/4/22
 * Time: 20:25
 */
public class LinkedListPanlindromeSolution {

    public static void main(String[] args) {
        ListNode head = new ListNode(-129);
        head.next = new ListNode(-129);

        System.out.println(isPalindrome(head));
    }

    private static boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }

        List<Integer> list = new ArrayList<Integer>();

        list.add(head.val);

        ListNode current = head.next;
        while (current != null) {
            list.add(current.val);
            current = current.next;
        }

        if (list.size() == 1) {
            return true;
        }

        for (int i = 0, j = list.size() - 1; i <= j; i++, j--) {
            if (list.get(i).intValue() != list.get(j)) {
                return false;
            }
        }

        return true;
    }

    public static class ListNode {

        int      val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
