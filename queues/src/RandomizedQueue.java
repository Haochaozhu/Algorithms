import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        items = (Item[]) new Object[8];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");
        items[size] = item;
        size += 1;
        if (size == items.length) resize(2 * items.length);
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int N = Math.min(copy.length, items.length);

        for (int i = 0; i < N; i += 1) {
            if (items[i] != null) copy[i] = items[i];
        }
        items = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException("Deque is empty");
        StdRandom.shuffle(items, 0, size - 1);
        Item res = items[size - 1];
        items[size - 1] = null;
        size -= 1;
        if (4 * size < items.length && items.length > 8) {
            resize(items.length / 2);
        }
        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) throw new NoSuchElementException("Deque is empty");
        StdRandom.shuffle(items, 0, size - 1);
        Item res = items[size - 1];
        return res;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomDequeIterator();
    }

    private class RandomDequeIterator implements Iterator<Item> {
        int count;

        RandomDequeIterator() {
            if (size == 0) count = 0;
            else count = size - 1;
            StdRandom.shuffle(items, 0, count);
        }

        public boolean hasNext() {
            return count >= 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Queue is empty");

            Item res = items[count];
            count -= 1;
            return res;
        }

        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        rq.enqueue(7);
        rq.enqueue(8);
        rq.enqueue(4);
        rq.enqueue(2);
        rq.enqueue(1);
        rq.enqueue(3);
        rq.enqueue(3);
        rq.enqueue(3);
        rq.enqueue(3);
        rq.enqueue(3);
        rq.enqueue(3);
        rq.enqueue(3);
        rq.enqueue(3);
        rq.enqueue(3);

        for (int i : rq) {
            StdOut.println(i);
        }
    }
}
