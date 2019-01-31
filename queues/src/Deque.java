import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        public Node next;
        public Node previous;
        public Item item;

        Node () {
            next = null;
            previous = null;
            item = null;
        }

        Node(Node next, Node previous, Item item){
            this.next = next;
            this.previous = previous;
            this.item = item;
        }
    }

    private int size;
    private Node sentinel;

    public Deque() {
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");

        sentinel.next = new Node(sentinel.next, sentinel, item);
        sentinel.next.next.previous = sentinel.next;

        size += 1;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");

        sentinel.previous = new Node(sentinel, sentinel.previous, item);
        sentinel.previous.previous.next = sentinel.previous;

        size += 1;
    }

    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException("Deque is empty");

        Item T = sentinel.next.item;
        sentinel.next.next.previous = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return T;
    }

    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException("Deque is empty");

        Item T = sentinel.previous.item;
        sentinel.previous.previous.next = sentinel;
        sentinel.previous = sentinel.previous.previous;
        size -= 1;
        return T;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        Node pointer;

        DequeIterator() {
            pointer = sentinel.next;
        }

        public boolean hasNext() {
            return pointer != sentinel;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Deque is empty");
            Item res = pointer.item;
            pointer = pointer.next;
            return res;
        }

        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported");
        }
    }

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(3);
        d.addFirst(4);
        d.addFirst(5);
        d.addFirst(6);
        d.addFirst(7);

        for (int i : d) {
            System.out.println(i);
        }

    }
}
