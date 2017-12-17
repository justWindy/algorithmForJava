package org.ray.algorithm.collections;

import org.ray.algorithm.StdIn;
import org.ray.algorithm.StdOut;

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {

    private Node<Item> first;
    private int        n;

    public Bag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    private static class Node<Item> {

        private Item item;

        private Node<Item> next;
    }

    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {

        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            return null;
        }
    }

}
