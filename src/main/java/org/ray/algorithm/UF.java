package org.ray.algorithm;

public class UF {

    private int[]  parent;
    private byte[] rank;
    private int    count;

    public UF(int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }

        this.count = count;
        this.parent = new int[count];
        this.rank = new byte[count];
        for (int i = 0; i < count; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) {
            return;
        }

        if (rank[rootP] < rank[rootQ]) {
            parent[rootP] = rootQ;
        } else if (rank[rootP] > rank[rootQ]) {
            parent[rootQ] = rootP;
        } else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }

        count--;
    }

    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
