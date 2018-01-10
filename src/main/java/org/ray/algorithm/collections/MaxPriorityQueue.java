package org.ray.algorithm.collections;

import org.ray.algorithm.StdOut;
import org.ray.algorithm.StdRandom;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * created by helanzhou
 * Date: 1/9/18
 * Time: 7:38 PM
 */
public class MaxPriorityQueue<Key> implements Iterable<Key> {

    private Key[]           priorityQueue;
    private int             n;
    private Comparator<Key> comparator;

    public MaxPriorityQueue(int initCapacity) {
        priorityQueue = (Key[]) new Object[initCapacity + 1];
        this.n = 0;
    }

    public MaxPriorityQueue() {
        this(1);
    }

    public MaxPriorityQueue(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        priorityQueue = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    public MaxPriorityQueue(Comparator<Key> comparator) {
        this(1, comparator);
    }

    public MaxPriorityQueue(Key[] keys) {
        n = keys.length;
        priorityQueue = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < n; i++) {
            priorityQueue[i + 1] = keys[i];
        }
        for (int k = n / 2; k >= 1; k--) {
            sink(k);
        }
        assert isMaxHeap();
    }

    public static void main(String[] args) {
        MaxPriorityQueue<Integer> priorityQueue = new MaxPriorityQueue<>();
        for (int i = 0; i < 20; i++) {
            priorityQueue.insert(StdRandom.uniform(100));
        }

        StdOut.println(Arrays.deepToString(priorityQueue.getPriorityQueue()));
    }

    public Key[] getPriorityQueue() {
        return priorityQueue;
    }

    public void setPriorityQueue(Key[] priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public Comparator<Key> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<Key> comparator) {
        this.comparator = comparator;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }

        return priorityQueue[1];
    }

    private int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity > n;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = priorityQueue[i];
        }
        priorityQueue = temp;
    }

    public void insert(Key x) {
        if (n >= priorityQueue.length - 1) {
            resize(2 * priorityQueue.length);
        }

        priorityQueue[++n] = x;
        swim(n);
        assert isMaxHeap();
    }

    public Key delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }

        Key max = priorityQueue[1];
        exchange(1, n--);
        sink(1);
        if ((n > 0) && (n == (priorityQueue.length - 1) / 4)) {
            resize(priorityQueue.length / 2);

        }
        assert isMaxHeap();
        return max;
    }

    @Override
    public Iterator<Key> iterator() {
        return null;
    }

    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) priorityQueue[i]).compareTo(priorityQueue[j]) < 0;
        } else {
            return comparator.compare(priorityQueue[i], priorityQueue[j]) < 0;
        }

    }

    private void exchange(int i, int j) {
        Key swap = priorityQueue[i];
        priorityQueue[i] = priorityQueue[j];
        priorityQueue[j] = swap;
    }

    private boolean isMaxHeap(int k) {
        if (k > n) {
            return true;
        }

        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= n && less(k, left)) {
            return false;
        }
        if (right <= n && less(k, right)) {
            return false;
        }

        return isMaxHeap(left) && isMaxHeap(right);
    }

    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exchange(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            exchange(k, j);
            k = j;
        }
    }

    private class HeapIterator implements Iterator<Key> {

        private MaxPriorityQueue<Key> copy;

        public HeapIterator() {
            if (comparator == null) {
                copy = new MaxPriorityQueue<>(size());
            } else {
                copy = new MaxPriorityQueue<>(size(), comparator);
            }
            for (int i = 1; i <= n; i++) {
                copy.insert(priorityQueue[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return copy.delMax();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
