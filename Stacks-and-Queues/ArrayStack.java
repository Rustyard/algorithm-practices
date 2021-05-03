import java.util.Iterator;

public class ArrayStack<Type> implements Iterable<Type>{
    private Type[] s;
    private int N = 0;

    @Override
    public Iterator<Type> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Type> {
        private int i = N;
        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        // last in first out
        public Type next() {
            return s[--i];
        }
    }

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
