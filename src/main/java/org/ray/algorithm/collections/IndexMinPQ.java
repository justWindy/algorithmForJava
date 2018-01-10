package org.ray.algorithm.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * created by helanzhou
 * Date: 1/10/18
 * Time: 1:07 PM
 */
public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {

    private int   maxN; //优先级队列里的容量, 即能够最多插入多少个元素
    private int   n; //优先级队列里面已经包含的元素, 已经插入多少个
    private int[] pq; //二叉堆, 基于索引的
    private int[] qp; //pq的反转, 即: qp[pq[i]] = pq[qp[i]] = i
    private Key[] keys; //keys[i]意味着i的优先级

    public IndexMinPQ(int maxN) {
        if (maxN < 0) {
            throw new IllegalArgumentException();
        }

        this.maxN = maxN;
        n = 0;
        keys = (Key[]) new Comparable[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        for (int i = 0; i <= maxN; i++) {
            qp[i] = -1;
        }
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean contains(int i) {
        if (i < 0 || i >= maxN) {
            throw new IndexOutOfBoundsException();

        }

        return qp[i] != -1;
    }

    public int size() {
        return n;
    }

    public void insert(int i, Key key) {
        if (i < 0 || i >= maxN) {
            throw new IndexOutOfBoundsException();
        }
        if (contains(i)) {
            throw new IllegalArgumentException("index is already in the priority queue");
        }
        n++;
        qp[i] = n;
        pq[n] = i;
        keys[i] = key;
        swim(n);
    }

    public int minIndex() {
        if (n == 0) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return pq[1];
    }

    public Key minKey() {
        if (n == 0) {
            throw new NoSuchElementException("Priority queue under flow");
        }
        return keys[pq[1]];
    }

    public int delMin() {
        if (n == 0) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        int min = pq[1];
        exchange(1, n--);
        sink(1);
        assert min == pq[n + 1];
        qp[min] = -1;
        pq[n + 1] = -1;

        return min;
    }

    public Key keyOf(int i) {
        if (i < 0 || i >= maxN) {
            throw new IndexOutOfBoundsException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        } else {
            return keys[i];
        }

    }

    public void changeKey(int i, Key key) {
        if (i < 0 || i >= maxN) {
            throw new IndexOutOfBoundsException();
        }

        if (!contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }

        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);

    }

    public void decreaseKey(int i, Key key) {
        if (i < 0 || i >= maxN) {
            throw new IndexOutOfBoundsException();
        }

        if (!contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }

        if (keys[i].compareTo(key) <= 0) {
            throw new IllegalArgumentException(
                    "Calling decreaseKey() with given argument would not strictly decrease the key");
        }

        keys[i] = key;
        swim(qp[i]);
    }

    public void increaseKey(int i, Key key) {
        if (i < 0 || i >= maxN) {
            throw new IndexOutOfBoundsException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }

        if (keys[i].compareTo(key) >= 0) {
            throw new IllegalArgumentException(
                    "Calling increaseKey() with given argument would not strictly increase the key");
        }
        keys[i] = key;
        sink(qp[i]);
    }

    public void delete(int i) {
        if (i < 0 || i >= maxN) {
            throw new IndexOutOfBoundsException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }
        int index = qp[i];
        exchange(index, n--);
        swim(index);
        sink(index);
        qp[i] = -1;
    }

    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void exchange(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            exchange(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            exchange(k, j);
            k = j;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {

        private IndexMinPQ<Key> copy;

        public HeapIterator() {
            copy = new IndexMinPQ<>(pq.length - 1);
            for (int i = 1; i <= n; i++) {
                copy.insert(pq[i], keys[pq[i]]);
            }
        }

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy.delMin();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
