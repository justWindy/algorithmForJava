package org.ray.algorithm.sort;

import org.ray.algorithm.StdOut;
import org.ray.algorithm.StdRandom;
import org.ray.algorithm.StopWatch;

/**
 * created by ray
 * Date: 06/01/2018
 * Time: 14:29
 */
public class SortCompare {

    public static double time(String alg, Double[] a) {
        StopWatch stopWatch = new StopWatch();
        if (alg.equalsIgnoreCase("Insertion")) {
            Insertion.sort(a);
        } else if (alg.equalsIgnoreCase("Selection")) {
            Selection.sort(a);
        } else if (alg.equalsIgnoreCase("Shell")) {
            Shell.sort(a);
        }
        return stopWatch.elapsedTime();
    }

    public static double timeRandomInput(String alg, int N, int T) {
        double total = 0.0;

        Double[] a = new Double[N];
        for (int t = 0; t < T; t++) {
            for (int i = 0; i < N; i++) {
                a[i] = StdRandom.uniform();
            }
            total += time(alg, a);
        }
        return total;
    }

    public static void main(String[] args) {
        String alg1 = args[0];
        String alg2 = args[1];

        int N = Integer.parseInt(args[2]);
        int T = Integer.parseInt(args[3]);
        double t1 = timeRandomInput(alg1, N, T);
        double t2 = timeRandomInput(alg2, N, T);

        StdOut.printf("For %d random Doubles\n  %s is", N, alg1);
        StdOut.printf(" %.1f times faster than %s\n", t2 / t1, alg2);
    }

}
