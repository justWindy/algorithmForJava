package org.ray.leetcode;

/**
 * leetCode 92
 *
 * @author ray
 * created by ray
 * Date: 2018/4/22
 * Time: 23:28
 */
public class ReverseBetweenSolution {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        /*head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);*/

        print(reverseBetween(head, 1, 2));
    }

    private static void print(ListNode listNode) {
        while (listNode != null) {
            System.out.print(listNode.val + "->");
            listNode = listNode.next;
        }
    }

    private static ListNode reverseBetween(ListNode head, int m, int n) {
        if (n == 1) {
            return head;
        }

        if (m == n) {
            return head;
        }

        if (1 <= m && m < n) {
            int index = 1;
            ListNode tail = new ListNode(0);
            ListNode start = new ListNode(0);
            ListNode end = new ListNode(0);
            ListNode prev = null;
            if (m == 1) {
                prev = head;
                index = 0;
            } else {
                prev = head.next;
            }

            start.next = head;
            while (prev != null) {
                if (index < m - 1) {
                    start.next = prev;
                    index++;
                    prev = prev.next;
                    continue;
                }

                if (index >= n) {
                    break;
                }

                ListNode current = new ListNode(prev.val);
                if (tail.next == null) {
                    end.next = current;
                    tail.next = current;
                } else {
                    ListNode temp = tail.next;
                    tail.next = current;
                    current.next = temp;
                }

                if (index > n) {
                    break;
                }

                index++;
                prev = prev.next;
            }

            if (index >= n) {
                end.next.next = prev;
            }

            if (m != 1) {
                start.next.next = tail.next;

            } else {
                return tail.next;
            }

            return head;
        }
        return null;
    }

    public static class ListNode {

        int      val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
