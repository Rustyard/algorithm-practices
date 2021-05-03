public class ArrayStack<Type> {
    private Type[] s;
    private int N = 0;

    // don't need to decide it's capacity
    public ArrayStack() {
        s = (Type[]) new Object[1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(Type item) {
        // double the array size when it's full
        if (N == s.length) {
            resize(2 * s.length);
        }
        s[N++] = item;
    }

    public Type pop() {
        if (isEmpty()) return null;
        Type item = s[--N];
        s[N] = null; // avoids memory leak (loitering)
        // shrink the array when it's one-quarter full
        if (N > 0 && N == s.length/4) resize(s.length/2);
        return item;
    }

    private void resize(int capacity) {
        Type[] copy = (Type[]) new Object[capacity];
        if (N >= 0) System.arraycopy(s, 0, copy, 0, N);
        s = copy;
    }
}
