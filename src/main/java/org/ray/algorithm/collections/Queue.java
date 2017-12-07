package org.ray.algorithm.collections;

import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int        n;

    private static class Node<Item> {

        private Item       item;
        private Node<Item> next;
    }

    @Override
    public Iterator<Item> iterator() {
        return null;
    }
}
