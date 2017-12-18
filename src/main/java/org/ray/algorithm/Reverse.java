package org.ray.algorithm;

import org.ray.algorithm.collections.Stack;

public class Reverse {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        while (!StdIn.isEmpty()) {
            stack.push(StdIn.readInt());
        }

        for (int i : stack) {
            StdOut.print(i + " ");
        }
    }

}
