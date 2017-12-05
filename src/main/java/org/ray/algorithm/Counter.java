package org.ray.algorithm;

public class Counter implements Comparable<Counter> {

    private final String name;
    private int count = 0;

    public Counter(String id) {
        name = id;
    }

    public void increment() {
        count++;
    }

    public int tally() {
        return count;
    }

    public String toString() {
        return count + " " + name;
    }

    @Override
    public int compareTo(Counter o) {
        if (this.count < o.count) {
            return -1;
        } else if (this.count > o.count) {
            return 1;
        } else {
            return 0;
        }
    }
}
