package org.ray.demo;

import org.ray.algorithm.StdIn;
import org.ray.algorithm.StdOut;
import org.ray.demo.standard.Stack;

/**
 * created by ray
 * Date: 25/03/2018
 * Time: 19:47
 */
public class Evaluate {

    public static void main(String[] args) {
        Stack<String> ops = new Stack<>();
        Stack<Double> vals = new Stack<>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();

            if (s.equals("(")) {

            } else if (s.equals("+")) {
                ops.push(s);
            } else if (s.equals("-")) {
                ops.push(s);
            } else if (s.equals("*")) {
                ops.push(s);
            } else if (s.equals("/")) {
                ops.push(s);
            } else if (s.equals("sqrt")) {
                ops.push(s);
            } else if (s.equals(")")) {
                String op = ops.pop();
                double v = vals.pop();

                if (op.equals("+")) {
                    v = vals.pop() + v;
                } else if (op.equals("*")) {
                    v = vals.pop() * v;
                } else if (op.equals("-")) {
                    v = vals.pop() - v;
                } else if (op.equals("/")) {
                    v = vals.pop() / v;
                } else if (op.equals("sqrt")) {
                    v = Math.sqrt(v);
                }

                vals.push(v);
            } else {
                vals.push(Double.parseDouble(s));
            }
        }

        StdOut.println(vals.pop());
    }
}
