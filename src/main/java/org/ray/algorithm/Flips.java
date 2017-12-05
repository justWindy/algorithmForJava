package org.ray.algorithm;

public class Flips {

    public static void main(String[] args) {
        int value = Integer.parseInt(args[0]);
        Counter heads = new Counter("heads");
        Counter tails = new Counter("tails");

        for (int i = 0; i < value; i++) {
            if (StdRandom.bernoulli(0.5)) {
                heads.increment();
            } else {
                tails.increment();
            }
        }

        StdOut.println(heads);
        StdOut.println(tails);
        int metrics = heads.tally() - tails.tally();

        StdOut.println("delta: " + Math.abs(metrics));
    }

}
