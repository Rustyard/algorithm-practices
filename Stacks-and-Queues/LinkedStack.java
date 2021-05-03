import java.util.Iterator;

public class LinkedStack<Type> implements Iterable<Type>{
    private Node first = null;

    @Override
    public Iterator<Type> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Type> {
        private Node current = first;
        @Override
        public boolean hasNext() {
            return current != null;
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

    public void push(Type item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
    }

    public Type pop() {
        if (isEmpty()) return null;
        Type item = first.item;
        first = first.next;
        return item;
    }
}