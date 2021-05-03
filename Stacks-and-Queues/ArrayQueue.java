public class ArrayQueue<Type> {
    private Type[] s;
    private int first = 0, last = 0;

    public ArrayQueue() {
        s = (Type[]) new Object[1];
    }

    public boolean isEmpty() {
        return first == last;
    }

    public void enqueue(Type item) {
        // double the array size when it's about to be full (one space before full)
        if ((last + 1) % s.length == first) {
            resize(s.length * 2);
        }
        s[last] = item;
        last = (last + 1) % s.length;
    }

    public Type dequeue() {
        if (isEmpty()) return null;
        Type item = s[first];
        first = (first + 1) % s.length;
        // shrink the array when it's one-quarter full
        if (last > first) {
            if (last - first <= s.length / 4) {
                resize(s.length / 2);
            }
        }
        else if (last + s.length - first <= s.length / 4) {
            resize(s.length / 2);
        }
        return item;
    }

    private void resize(int capacity) {
        Type[] copy = (Type[]) new Object[capacity];
        int newLast = 0;
        for (int N = first; N < last; N = (N + 1) % s.length) {
            copy[newLast++] = s[N];
        }
        first = 0;
        last = newLast;
        s = copy;
    }

}