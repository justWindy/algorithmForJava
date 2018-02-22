package org.ray.chuanda;

/**
 * created by ray
 * Date: 22/02/2018
 * Time: 14:58
 */
public class Permutation<T> {

    public static void main(String[] args) {
        Permutation<String> permutation = new Permutation<>();
        String string = "a,b,c";
        String[] array = string.split(",");
        permutation.perm(array, 0, array.length - 1);
    }

    private void perm(T[] array, int k, int m) {
        if (k == m) {
            for (T t : array) {
                System.out.print(t);
            }
            System.out.println();
        } else {
            for (int i = k; i <= m; i++) {
                swap(array, k, i);
                perm(array, k + 1, m);
                swap(array, k, i);
            }
        }
    }

    private void swap(T[] list, int a, int b) {
        T temp = list[a];
        list[a] = list[b];
        list[b] = temp;
    }
}
