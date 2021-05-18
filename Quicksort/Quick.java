import edu.princeton.cs.algs4.StdRandom;

public class Quick {
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (less(a[++i], a[lo])) // find item on left to swap
                if (i == hi) break;
            while (less(a[lo], a[--j])) // find item on right to swap
                if (j == lo) break; // this is actually redundant (not needed)
            if (i >= j) break; // check if pointers cross
            exchange(a, i, j); // swap
        }

        exchange(a, lo, j); // swap with partitioning point
        return j; // return index of item now known to be in place
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a); // shuffle needed for performance guarantee
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        /*
         * Improvement speed by ~20% by doing insertion sort on small subarrays (~10-20 items)
         * if (hi <= lo + CUTOFF - 1)
         * {
         *       Insertion.sort(a, lo, hi);
         *       return;
         * }
         */
        if (hi <= lo) return; // nothing needs to be done when only 1 element
        /*
         * Improvement speed by ~10% by choosing better partition point (median point)
         * we estimate true median by taking median of sample, take 3 random items and
         * choose the median of them to be the partition point.
         * reduces compares and increases exchanges.
         *
         * int m = medianOf3(a, lo, lo+(hi-lo)/2, hi);
         * exchange(a, lo, m);
         *
         */
        int j = partition(a, lo, hi); // after this, element j will be in position
        sort(a, lo, j - 1); // do the same thing to the left subarray
        sort(a, j + 1, hi); // and right subarray
    }

    /**
     * Quick-select based on quicksort (uses partitioning).
     * Select the k-th biggest item in the array a, in linear time on average.
     *
     * @param a The array to select.
     * @param k Demands the returning item to be k-th biggest
     * @return The k-th biggest item in array a.
     */
    public static Comparable select(Comparable[] a, int k) {
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k) lo = j + 1;
            else if (j > k) hi = j - 1;
            else return a[k];
        }
        return a[k];
    }

    /**
     * 3-way Quicksort: the faster quicksort for HUGE DUPES, better than the original.
     * Basic idea: partitioning the array into 3-parts: less than partition
     * point, equal to partition point, and greater than partition point.
     * It's also the fast solution for Dutch National Flag problem by Dijkstra.
     *
     * @param a  The array to be sorted.
     * @param lo The lower bound index.
     * @param hi The higher bound index.
     */
    public static void sort3p(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, gt = hi; // less than and greater than: used in 3-way partitioning
        Comparable v = a[lo]; // partitioning point
        int i = lo; // current point
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) exchange(a, lt++, i++);
            else if (cmp > 0) exchange(a, i, gt--);
            else i++;
        }

        sort3p(a, lo, lt - 1);
        sort3p(a, gt + 1, hi);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }
}
