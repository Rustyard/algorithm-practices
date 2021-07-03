import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;

// a MaxPQ with sample() and delRandom() method
public class RandomMaxPQ<Key extends Comparable<Key>> {
    private final Key[] pq; // note: pq[0] is unused
    private int N; // number of objects

    public RandomMaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity];
    }

    public static void main(String[] args) {
        int[] testNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        RandomMaxPQ<Integer> test = new RandomMaxPQ<>(15);

        for (int number : testNumbers) {
            test.insert(number);
        }

//        for (int i = 0; i < 50; i++) {
//            System.out.print(test.sample() + " ");
//        }
        while (!test.isEmpty()) {
            System.out.println(test.delRandom());
        }
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key key) {
        pq[++N] = key;
        swim(N);
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exchange(k, k / 2);
            k = k / 2;
        }
    }

    public Key delMax() {
        Key max = pq[1];
        exchange(1, N--);
        sink(1);
        pq[N + 1] = null;
        return max;
    }

    public Key sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return pq[StdRandom.uniform(1, N + 1)];
    }

    public Key delRandom() {
        if (isEmpty()) throw new NoSuchElementException();
        int random = StdRandom.uniform(1, N + 1); // N included
        Key item = pq[random];
        exchange(random, N--);
        if (random != N + 1) // if item happened to be the very last one in the pq, then sinking is not required
            sink(random);
        pq[N + 1] = null;
        return item;
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exchange(k, j);
            k = j;
        }
    }

    private boolean less(int v, int w) {
        return pq[v].compareTo(pq[w]) < 0;
    }

    private void exchange(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
}
