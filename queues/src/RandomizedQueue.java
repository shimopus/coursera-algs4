import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[2];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++)
            copy[i] = queue[i];

        queue = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == queue.length) {
            resize(queue.length*2);
        }

        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        if (size > 0 && size == queue.length/4) {
            resize(queue.length/2);
        }

        StdRandom.shuffle(queue, 0, size);

        Item ret = queue[size - 1];
        queue[size-1] = null;
        size--;

        return ret;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        StdRandom.shuffle(queue, 0, size);

        return queue[size - 1];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private static void print(RandomizedQueue queue) {
        for (Object item : queue) {
            System.out.print(item + ", ");
        }

        System.out.println("\n__________");
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        print(queue);

        System.out.println(queue.dequeue());
        print(queue);
        System.out.println(queue.dequeue());
        print(queue);
        System.out.println(queue.dequeue());
        print(queue);
        System.out.println(queue.dequeue());
        print(queue);
        System.out.println(queue.dequeue());
        print(queue);


        queue.enqueue(4);
        queue.enqueue(5);
        System.out.println(queue.sample());
        print(queue);
        System.out.println(queue.sample());
        print(queue);
        System.out.println(queue.sample());
        print(queue);
    }

    class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] localQueue = (Item[]) new Object[size];
        int current = 0;

        public RandomizedQueueIterator() {
            System.arraycopy(queue, 0, localQueue, 0, size);
            StdRandom.shuffle(localQueue);
        }

        @Override
        public boolean hasNext() {
            return localQueue.length > current;
        }

        @Override
        public Item next() {
            return localQueue[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}