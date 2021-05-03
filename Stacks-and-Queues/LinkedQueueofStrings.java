import java.util.SplittableRandom;

public class LinkedQueueofStrings {
    private Node first, last;

    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(String item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        // special cases
        if (isEmpty()) first = last;
        else oldlast.next = last;
    }

    public String dequeue() {
        if (isEmpty()) return null;
        String item = first.item;
        first = first.next;
        // special cases
        if (isEmpty()) last = null;
        return item;
    }

}
