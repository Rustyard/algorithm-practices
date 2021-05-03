public class LinkedStack<Type> {
    private Node first = null;

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