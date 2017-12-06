package org.ray.algorithm;

public class Interval2D {

    private final Interval1D x;
    private final Interval1D y;

    public Interval2D(Interval1D x, Interval1D y) {
        this.x = x;
        this.y = y;
    }

    public boolean intersects(Interval2D that) {
        if (!this.x.intersects(that.x)) {
            return false;
        }

        if (!this.y.intersects(that.y)) {
            return false;
        }

        return true;
    }

    public boolean contains(Point2D point2D) {
        return x.contains(point2D.x()) && y.contains(point2D.y());
    }

    public double area() {
        return x.length() * y.length();
    }

    public String toString() {
        return x + " x " + y;
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

        Interval2D that = (Interval2D) other;
        return this.x.equals(that.x) && this.y.equals(that.y);
    }

    public int hashCode() {
        int hash1 = x.hashCode();
        int hash2 = y.hashCode();

        return 31 * hash1 + hash2;
    }

    public void draw() {
        double xc = (x.min() + x.max()) / 2.0;
        double yc = (y.min() + y.max()) / 2.0;

        StdDraw.rectangle(xc, yc, x.length() / 2.0, y.length() / 2.0);
    }
}
