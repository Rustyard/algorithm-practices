public class ResizingArrayStackOfStrings {
    private String[] s;
    private int N = 0;

    // don't need to decide it's capacity
    public ResizingArrayStackOfStrings() {
        s = new String[1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(String item) {
        // double the array size when it's full
        if (N == s.length) {
            resize(2 * s.length);
        }
        s[N++] = item;
    }

    public String pop() {
        String item = s[--N];
        s[N] = null; // avoids memory leak (loitering)
        // shrink the array when it's one-quarter full
        if (N > 0 && N == s.length/4) resize(s.length/2);
        return item;
    }

    private void resize(int capacity) {
        String[] copy = new String[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }
}
