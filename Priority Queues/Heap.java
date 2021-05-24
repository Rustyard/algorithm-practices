public class Heap<Key extends Comparable<Key>> {
    // heap sort: In-place sorting algorithm with NlogN worst-case
    // not stable
    public static void sort(Comparable[] pq) {
        int N = pq.length;
        for (int k = N / 2; k >= 1; k--)
            sink(pq, k, N); // construct the max-heap ( <= 2N compares and exchanges)

        while (N > 1) {
            exchange(pq, 1, N); // move the maximum item to the end
            sink(pq, 1, --N); // sink the new root in a size-decremented heap
            // <= 2NlgN compares and exchanges
        }
    }

    private static void sink(Comparable[] pq, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(pq, j, j + 1)) j++;
            if (!less(pq, k, j)) break;
            exchange(pq, k, j);
            k = j;
        }
    }


    // these two methods are converted from 1-base indexing to 0-base indexing
    private static boolean less(Comparable[] pq, int v, int w) {
        return pq[--v].compareTo(pq[--w]) < 0;
    }

    private static void exchange(Comparable[] pq, int i, int j) {
        Comparable t = pq[--i];
        pq[i] = pq[--j];
        pq[j] = t;
    }
}
