public class LinkedQueue<Type> {
    private Node first, last;

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
