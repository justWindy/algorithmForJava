package org.ray.algorithm;

import java.util.Random;

public final class StdRandom {

    private static Random random;
    private static long   seed;

    static {
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    private StdRandom() {
    }

    public static long getSeed() {
        return seed;
    }

    public static void setSeed(long s) {
        seed = s;
        random = new Random(seed);
    }

    public static double uniform() {
        return random.nextDouble();
    }

    public static int uniform(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("argument must be positive");
        }
        return random.nextInt(n);
    }

    public static int uniform(int a, int b) {
        if ((b <= a) || ((long) b - a >= Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + "]");
        }

        return a + uniform(b - a);
    }

    public static double uniform(double a, double b) {
        if (!(a < b)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + "]");
        }
        return a + uniform() * (b - a);
    }

    public static boolean bernoulli(double p) {
        if (!(p >= 0.0 && p <= 1.0)) {
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0");
        }

        return uniform() < p;
    }

    public static boolean bernoulli() {
        return bernoulli(0.5);
    }

    public static double gaussian() {
        double r, x, y;
        do {
            x = uniform(-1.0, 1.0);
            y = uniform(-1.0, 1.0);
            r = x * x + y * y;
        } while (r >= 1 || r == 0);

        return x * Math.sqrt(-2 * Math.log(r) / r);
    }

    public static double gaussian(double mu, double sigma) {
        return mu + sigma * gaussian();
    }

    public static int geometric(double p) {
        if (!(p >= 0.0 && p <= 1.0)) {
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0");
        }

        return (int) Math.ceil(Math.log(uniform()) / Math.log(1.0 - p));
    }

    public static int poisson(double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("lambda must be positive");
        }

        if (Double.isInfinite(lambda)) {
            throw new IllegalArgumentException("lambda must not be infinite");
        }

        int k = 0;
        double p = 1.0;
        double expLambda = Math.exp(-lambda);
        do {
            k++;
            p *= uniform();
        } while (p >= expLambda);
        return k - 1;
    }

    public static double pareto(double alpha) {
        if (!(alpha > 0.0)) {
            throw new IllegalArgumentException("alpha must be positive");
        }

        return Math.pow(1 - uniform(), -1.0 / alpha) - 1.0;
    }

    public static double cauchy() {
        return Math.tan(Math.PI * (uniform() - 0.5));
    }

    public static int discrete(double[] probabilities) {
        if (probabilities == null) {
            throw new IllegalArgumentException("argument array is null");
        }

        double EPSILON = 1E-14;
        double sum = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            if (!(probabilities[i] >= 0.0)) {
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: " + probabilities[i]);
            }
            sum += probabilities[i];
        }

        if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON) {
            throw new IllegalArgumentException("sum of array entries does not approximately equal 1.0: " + sum);
        }

        while (true) {
            double r = uniform();
            sum = 0.0;
            for (int i = 0; i < probabilities.length; i++) {
                sum += probabilities[i];
                if (sum > r) {
                    return i;
                }
            }
        }
    }

    public static int discrete(int[] frequencies) {
        if (frequencies == null) {
            throw new IllegalArgumentException("argument array is null");
        }
        long sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] < 0) {
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: " + frequencies[i]);
            }
            sum += frequencies[i];
        }

        if (sum == 0) {
            throw new IllegalArgumentException("at least one array entry must be positive");
        }

        if (sum >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("sum of frequencies overflows an int");
        }

        double r = uniform((int) sum);
        sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            sum += frequencies[i];
            if (sum > r) {
                return i;
            }
        }

        assert false;
        return -1;
    }

    public static double exp(double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("lambda must be positive");
        }

        return -Math.log(1 - uniform()) / lambda;
    }

    public static void shuffle(Object[] a) {
        if (a == null) {
            throw new IllegalArgumentException("argument array is null");
        }

        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n - i);
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(double[] a) {
        if (a == null) {
            throw new IllegalArgumentException("argument array is null");
        }

        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n - i);
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(int[] a) {
        if (a == null) {
            throw new IllegalArgumentException("argument array is null");
        }
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(n - i);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(char[] a) {
        if (a == null) {
            throw new IllegalArgumentException("argument array is null");
        }

        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = uniform(n - i);
            char temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(Object[] a, int low, int high) {
        if (a == null) {
            throw new IndexOutOfBoundsException("invalid subarray range: [" + low + ", " + high + ")");
        }

        if (low < 0 || low >= high || high > a.length) {
            throw new IndexOutOfBoundsException("invalid subarray range: [" + low + ", " + high + ")");
        }
        for (int i = low; i < high; i++) {
            int r = i + uniform(high - i);
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(int[] a, int low, int high) {
        if (a == null) {
            throw new IndexOutOfBoundsException("invalid subarray range: [" + low + ", " + high + ")");
        }

        if (low < 0 || low >= high || high > a.length) {
            throw new IndexOutOfBoundsException("invalid subarray range: [" + low + ", " + high + ")");
        }
        for (int i = low; i < high; i++) {
            int r = i + uniform(high - i);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(double[] a, int low, int high) {
        if (a == null) {
            throw new IndexOutOfBoundsException("invalid subarray range: [" + low + ", " + high + ")");
        }

        if (low < 0 || low >= high || high > a.length) {
            throw new IndexOutOfBoundsException("invalid subarray range: [" + low + ", " + high + ")");
        }
        for (int i = low; i < high; i++) {
            int r = i + uniform(high - i);
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static int[] permutation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("argument is negative");
        }
        int[] perm = new int[n];

        for (int i = 0; i < n; i++) {
            perm[i] = i;
        }
        shuffle(perm);
        return perm;
    }

    public static int[] permutation(int n, int k) {
        if (n < 0) {
            throw new IllegalArgumentException("argument is negative");
        }

        if (k < 0 || k > n) {
            throw new IllegalArgumentException("k must be between 0 and n");
        }

        int[] perm = new int[k];
        for (int i = 0; i < k; i++) {
            int r = uniform(i + 1);
            perm[i] = perm[r];
            perm[r] = i;
        }

        for (int i = k; i < n; i++) {
            int r = uniform(i + 1);
            if (r < k) {
                perm[r] = r;
            }
        }

        return perm;
    }
}
