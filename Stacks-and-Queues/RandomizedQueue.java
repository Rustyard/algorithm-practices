import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// randomized queue implemented using array
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size; // functioning as size and stack top at the same time

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> integerRandomizedQueue = new RandomizedQueue<>();

        int[] data = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int datum : data) {
            integerRandomizedQueue.enqueue(datum);
        }

        System.out.println(integerRandomizedQueue.sample());
        System.out.println(integerRandomizedQueue.sample());
        System.out.println(integerRandomizedQueue.dequeue());
        System.out.println(integerRandomizedQueue.dequeue());

        for (int item : integerRandomizedQueue) {
            System.out.print(item + " ");
        }
        System.out.println();

        for (int item : integerRandomizedQueue) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == items.length) resize(2 * items.length);

        items[size++] = item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException();
        if (size <= items.length / 4) resize(items.length / 2);

        int randomIndex = StdRandom.uniform(0, size);
        Item returnItem = items[randomIndex];
        items[randomIndex] = items[--size]; // swap stack top
        items[size] = null;
        return returnItem;
    }

    public Item sample() {
        if (size == 0) throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(0, size);
        return items[randomIndex];
    }

    private void resize(int sizeResize) {
        Item[] newArray = (Item[]) new Object[sizeResize];
        if (size >= 0) System.arraycopy(items, 0, newArray, 0, size);
        items = newArray;
    }

    private class ListIterator implements Iterator<Item> {
        private int currentSize = size;
        private Item[] list;

        ListIterator() {
            if (size > 0) {
                list = (Item[]) new Object[size];
                System.arraycopy(items, 0, list, 0, size);
            }
        }

        @Override
        public boolean hasNext() {
            return currentSize > 0;
        }

        // similar to dequeue
        @Override
        public Item next() {
            if (currentSize == 0) throw new NoSuchElementException();
            int random = StdRandom.uniform(0, currentSize);
            Item returnItem = list[random];
            list[random] = list[--currentSize];
            list[currentSize] = null;
            return returnItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
