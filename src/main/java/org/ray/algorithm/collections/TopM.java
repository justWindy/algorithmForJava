package org.ray.algorithm.collections;

import org.ray.algorithm.StdIn;
import org.ray.algorithm.StdOut;
import org.ray.algorithm.Transaction;

/**
 * created by ray
 * Date: 10/01/2018
 * Time: 08:29
 */
public class TopM {

    private TopM() {

    }

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);

        MinPQ<Transaction> pq = new MinPQ<>(m + 1);

        while (StdIn.hasNextLine()) {
            String line = StdIn.readLine();

            Transaction transaction = new Transaction(line);
            pq.insert(transaction);

            if (pq.size() > m) {
                pq.delMin();
            }
        }

        Stack<Transaction> stack = new Stack<>();
        for (Transaction transaction : pq) {
            stack.push(transaction);
        }

        for (Transaction transaction : stack) {
            StdOut.println(transaction);
        }
    }

}
