public class ResizingArrayQueueOfStrings {
    private String[] s;
    private int first = 0, last = 0;

    public ResizingArrayQueueOfStrings() {
        s = new String[1];
    }

    public boolean isEmpty() {
        return first == last;
    }

    // TODO: finish this
    public void enqueue(String item) {

    }

    // TODO: finish this
    public String dequeue() {
        return null;
    }

    // TODO: finish this
    private void resize(int capacity) {
        String[] copy = new String[capacity];
        // copy from s to new string
        s = copy;
    }
}
