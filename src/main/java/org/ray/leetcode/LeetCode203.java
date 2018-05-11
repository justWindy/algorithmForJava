package org.ray.leetcode;

import org.omg.PortableServer.LIFESPAN_POLICY_ID;

/**
 * leetcode 203
 *
 * @author ray
 * created by ray
 * Date: 2018/4/30
 * Time: 09:30
 */
public class LeetCode203 {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(6);
        node1.next = new ListNode(2);
        node1.next.next = new ListNode(3);
        ListNode node2 = new ListNode(6);
        node1.next.next.next = node2;
        node2.next = new ListNode(4);
        node2.next.next = new ListNode(5);
        node2.next.next.next = new ListNode(6);


        ListNode node3 = new ListNode(1);
        node3.next = new ListNode(1);
        printListNode(removeElements(node1, 6));

    }

    private static void printListNode(ListNode listNode) {

        while (listNode != null) {
            System.out.print(listNode.val + " -> ");
            listNode = listNode.next;
        }
    }

    private static ListNode removeElements(ListNode head, int val) {
        ListNode position = new ListNode(0);
        ListNode current = new ListNode(0);
        position.next = current;
        current.next = head;
        while (head != null) {
            if (head.val == val) {
                current.next = head.next;
            } else {
                current = current.next;
            }
            head = head.next;
        }

        return position.next.next;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}
