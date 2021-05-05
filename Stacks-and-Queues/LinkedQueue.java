import java.util.Iterator;

public class LinkedQueue<Type> implements Iterable<Type>{
    private Node first, last;

    @Override
    public Iterator<Type> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Type> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != last;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Type next() {
            Type item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node {
        Type item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(Type item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        // special cases
        if (isEmpty()) first = last;
        else oldlast.next = last;
    }

    public Type dequeue() {
        if (isEmpty()) return null;
        Type item = first.item;
        first = first.next;
        // special cases
        if (isEmpty()) last = null;
        return item;
    }

}
