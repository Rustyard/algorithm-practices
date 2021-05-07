import java.util.Iterator;
import java.util.NoSuchElementException;

// double-linked list deque
public class Deque<Item> implements Iterable<Item>{
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;

        Node() {
            item = null;
        }
        Node(Item item) {
            this.item = item;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node newNode = new Node(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
            size = 1;
        }
        else {
            Node oldFirst = first;
            first = newNode;
            first.next = oldFirst;
            oldFirst.prev = first;
            size++;
        }
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node newNode = new Node(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
            size = 1;
        }
        else {
            Node oldLast = last;
            last = newNode;
            last.prev = oldLast;
            oldLast.next = last;
            size++;
        }
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        if (first == last) {
            first = null;
            last = null;
            size = 0;
        }
        else {
            first = first.next;
            first.prev = null;
            size--;
        }
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        if (first == last) {
            first = null;
            last = null;
            size = 0;
        }
        else {
            last = last.prev;
            last.next = null;
            size--;
        }
        return item;
    }

    public static void main(String[] args) {
        Deque<String> stringDeque = new Deque<>();
        System.out.println("A deque has been newed, it's size is " + stringDeque.size());

        System.out.println("Add-first string: 1st string");
        stringDeque.addFirst("1st string");
        System.out.println("Size is " + stringDeque.size());
        System.out.println("ITEMS ARE: ");
        for (String s : stringDeque) {
            System.out.print(s + ",");
        }
        System.out.println("\n");

        System.out.println("Add-last string: last string");
        stringDeque.addLast("last string");
        System.out.println("Size is " + stringDeque.size());
        System.out.println("ITEMS ARE: ");
        for (String s : stringDeque) {
            System.out.print(s + ",");
        }
        System.out.println("\n");

        System.out.println("Add-first string: first string");
        stringDeque.addFirst("first string");
        System.out.println("Size is " + stringDeque.size());
        System.out.println("ITEMS ARE: ");
        for (String s : stringDeque) {
            System.out.print(s + ",");
        }
        System.out.println("\n");

        System.out.println("Remove-last string: " + stringDeque.removeLast());
        System.out.println("Size is " + stringDeque.size());
        System.out.println("ITEMS ARE: ");
        for (String s : stringDeque) {
            System.out.print(s + ",");
        }
        System.out.println("\n");

        System.out.println("Remove-first string: " + stringDeque.removeFirst());
        System.out.println("Size is " + stringDeque.size());
        System.out.println("ITEMS ARE: ");
        for (String s : stringDeque) {
            System.out.print(s + ",");
        }
        System.out.println("\n");

        System.out.println("Remove-last string: " + stringDeque.removeLast());
        System.out.println("Size is " + stringDeque.size());
        System.out.println("ITEMS ARE: ");
        for (String s : stringDeque) {
            System.out.print(s + ",");
        }
        System.out.println("\n");
    }
}
