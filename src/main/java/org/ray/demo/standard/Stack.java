package org.ray.demo.standard;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * created by ray
 * Date: 25/03/2018
 * Time: 19:14
 */
public class Stack<Item> implements Iterable<Item> {

    private Node<Item> first;
    private int        n;

    public Stack() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void push(Item item) {
        Node<Item> oldFirst = first;

        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }

        Item item = first.item;
        first = first.next;
        n--;
        return item;
    }

    public Item peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }
        return first.item;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Item item : this) {
            builder.append(item).append(' ');
        }
        return builder.toString();
    }

    @Override
    public Iterator<Item> iterator() {
        return null;
    }

    private static class Node<Item> {

        private Item       item;
        private Node<Item> next;
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
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
