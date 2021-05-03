public class ResizingArrayQueueOfStrings {
    private String[] s;
    private int first = 0, last = 0;

    public ResizingArrayQueueOfStrings() {
        s = new String[1];
    }

    public boolean isEmpty() {
        return first == last;
    }

    public void enqueue(String item) {
        // double the array size when it's about to be full (one space before full)
        if ((last + 1) % s.length == first) {
            resize(s.length * 2);
        }
        s[last] = item;
        last = (last + 1) % s.length;
    }

    public String dequeue() {
        if (isEmpty()) return null;
        String item = s[first];
        first = (first + 1) % s.length;
        // shrink the array when it's one-quarter full
        if (last > first) {
            if (last - first <= s.length / 4) {
                resize(s.length / 2);
            }
        }
        else {
            if (last + s.length - first <= s.length / 4) {
                resize(s.length / 2);
            }
        }
        return item;
    }

    private void resize(int capacity) {
        String[] copy = new String[capacity];
        int newLast = 0;
        for (int N = first; N < last; N = (N + 1) % s.length) {
            copy[newLast++] = s[N];
        }
        first = 0;
        last = newLast;
        s = copy;
    }

    public static void main(String[] args) {
        ResizingArrayQueueOfStrings queueOfStrings = new ResizingArrayQueueOfStrings();
        String string1 = "happy";
        String string2 = "sad";
        String string3 = "apple";
        String string4 = "watermelon";
        queueOfStrings.enqueue(string1);
        queueOfStrings.enqueue(string2);
        queueOfStrings.enqueue(string3);
        queueOfStrings.enqueue(string4);
        queueOfStrings.dequeue();
        queueOfStrings.dequeue();
        queueOfStrings.dequeue();
        queueOfStrings.dequeue();
    }
}
