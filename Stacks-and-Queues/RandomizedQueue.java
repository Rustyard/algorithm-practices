import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

// randomized queue implemented using array
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size; // functioning as size and stack top at the same time

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    public static void main(String[] args) {

    }

    @Override
    public Iterator<Item> iterator() {
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (size == items.length) {
            resize(2 * items.length);
        }
        items[size++] = item;
    }

    public Item dequeue() {
        int randomIndex = StdRandom.uniform(0, size);
        Item returnItem = items[randomIndex];
        items[randomIndex] = items[--size]; // swap stack top
        items[size] = null;
        return returnItem;
    }

    private void resize(int sizeResize) {
        Item[] newArray = (Item[]) new Object[sizeResize];
        if (size >= 0) System.arraycopy(items, 0, newArray, 0, size);
        items = newArray;
    }

    public Item sample() {
        int randomIndex = StdRandom.uniform(0, size);
        return items[randomIndex];
    }
}
