package org.ray.algorithm.statistics;

import org.ray.algorithm.StdIn;
import org.ray.algorithm.StdOut;
import org.ray.algorithm.collections.ST;

/**
 * created by ray
 * Date: 30/01/2018
 * Time: 23:37
 */
public class FrequencyCounter {

    private FrequencyCounter() {

    }

    public static void main(String[] args) {
        int distinct = 0, words = 0;

        int minLen = Integer.parseInt(args[0]);

        ST<String, Integer> st = new ST<>();

        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            if (key.length() < minLen) {
                continue;
            }

            words++;
            if (st.contains(key)) {
                st.put(key, st.get(key) + 1);
            } else {
                st.put(key, 1);
                distinct++;
            }
        }

        String max = "";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max)) {
                max = word;
            }
        }

        StdOut.println(max + " " + st.get(max));
        StdOut.println("distinct = " + distinct);
        StdOut.println("words    = " + words);
    }

}
