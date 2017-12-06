package org.ray.algorithm;

import java.util.Comparator;

public class Interval1D {

    public static final Comparator<Interval1D> MIN_ENDPOINT_ORDER = new MinEndpointComparator();

    public static final Comparator<Interval1D> MAX_ENDPOINT_ORDER = new MaxEndpointComparator();

    public static final Comparator<Interval1D> LENGTH_ORDER = new LengthComparator();

    private final double min;
    private final double max;

    public Interval1D(double min, double max) {
        if (Double.isInfinite(min) || Double.isInfinite(max)) {
            throw new IllegalArgumentException("Endpoints must be finite");
        }

        if (Double.isNaN(min) || Double.isNaN(max)) {
            throw new IllegalArgumentException("Endpoints cannot be NaN");
        }

        if (min == 0.0) {
            min = 0.0;
        }

        if (max == 0.0) {
            max = 0.0;
        }

        if (min < max) {
            this.min = min;
            this.max = max;
        } else {
            throw new IllegalArgumentException("Illegal interval");
        }
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public boolean intersects(Interval1D that) {
        if (this.max < that.min) {
            return false;
        }

        if (that.max < this.min) {
            return false;
        }

        return true;
    }

    public boolean contains(double x) {
        return (min <= x) && (x <= max);
    }

    public double length() {
        return max - min;
    }

    public String toString() {
        return "[" + min + ", " + max + "]";
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Interval1D that = (Interval1D) other;
        return this.min == that.min && this.max == that.max;

    }

    public int hashCode() {
        int hash1 = ((Double) min).hashCode();
        int hash2 = ((Double) max).hashCode();

        return 31 * hash1 + hash2;
    }

    private static class MinEndpointComparator implements Comparator<Interval1D> {

        @Override
        public int compare(Interval1D o1, Interval1D o2) {
            if (o1.min < o2.min) {
                return -1;
            } else if (o1.min > o2.min) {
                return 1;
            } else if (o1.max < o2.max) {
                return -1;
            } else if (o1.max > o2.max) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private static class MaxEndpointComparator implements Comparator<Interval1D> {

        @Override
        public int compare(Interval1D o1, Interval1D o2) {
            if (o1.min < o2.max) {
                return -1;
            } else if (o1.min > o2.max) {
                return 1;
            } else if (o1.min < o2.min) {
                return -1;
            } else if (o1.min > o2.min) {
                return 1;
            } else {
                return 0;
            }

        }
    }

    private static class LengthComparator implements Comparator<Interval1D> {

        @Override
        public int compare(Interval1D o1, Interval1D o2) {
            double length1 = o1.length();
            double length2 = o2.length();

            if (length1 < length2) {
                return -1;
            } else if (length1 > length2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
