package org.ray.demo;

/**
 * created by ray
 * Date: 25/03/2018
 * Time: 17:20
 */
public class EuclidAlgorithm {

    public static int gcd(int p, int q) {
        if (q == 0) {
            return p;
        }

        int r = p % q;
        return gcd(q, r);
    }

    public static void main(String[] args) {
        int result = gcd(20, 8);
        System.out.println("gcd result:" + result);
    }
}
