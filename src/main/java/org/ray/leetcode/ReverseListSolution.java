package org.ray.leetcode;

/**
 * leetCode 206
 *
 * @author ray
 * created by ray
 * Date: 2018/4/22
 * Time: 21:11
 */
public class ReverseListSolution {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        print(reverseList(head));
    }

    private static ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode tail = new ListNode(0);
        ListNode prev = head.next;

        if (prev == null) {
            return head;
        }

        tail.next = head;
        head.next = null;

        while (prev != null) {
            ListNode current = new ListNode(prev.val);
            ListNode temp = tail.next;
            tail.next = current;
            current.next = temp;
            prev = prev.next;
        }

        return tail.next;
    }

    private static void print(ListNode listNode) {
        while (listNode != null) {
            System.out.print(listNode.val + "->");
            listNode = listNode.next;
        }
    }

    public static class ListNode {

        int      val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
