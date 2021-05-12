public class Merge {
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);
        // copy the array
        if (hi + 1 - lo >= 0) System.arraycopy(a, lo, aux, lo, hi + 1 - lo);

        // merge into original array
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
        assert isSorted(a, lo, hi);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        /*
        improvement of above line: use insertion sort for small subarrays, making it faster.
        if (hi <= lo + CUTOFF - 1) { // CUTOFF is about 7
            // Insertion.sort(a, lo, hi);
            return;
        }
        */
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        // improvement 2: stop if already sorted, making it linear-timed on best-cases
        if (!less(a[mid + 1], a[mid])) return;
        merge(a, aux, lo, mid, hi);
    }

    /* Improvement 3 Eliminate the copy to the auxiliary array:
    This will have to modify the merge function and sort function, see the comments below.

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        ...
        // DON'T COPY THE ARRAY (NOT NEEDED IN THIS CASE)

        for (int k = lo; k <= hi; k++) {
            if (i > mid)                    aux[k] = a[j++];
            else if (j > hi)                aux[k] = a[i++];
            else if (less(aux[j], aux[i]))  aux[k] = a[j++];  // MERGE FROM a[] TO aux[]
            else                            aux[k] = a[i++];
        }
        ...
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(aux, a, lo, mid);
        sort(aux, a, mid+1, hi);     // NOTE: sort(a) INITIALIZES aux[] AND SETS aux[i] = a[i] FOR EACH i, see below.
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        System.arraycopy(a, lo, aux, lo, hi + 1 - lo);  // SUMMARY: THIS WAY WE ONLY NEED TO DO THIS ONE SINGLE COPY IN THE WHOLE PROCESS.
        sort(a, aux, 0, a.length - 1);
    }
    */

    public static void sort(Comparable[] a) {
        // new the copy array here, not in the recursive function, otherwise
        // it will be consuming too much space and that should be considered as a bug
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /**
     * Return if the array a[] is sorted from index lo to hi inclusive.
     *
     * @param a  The comparable array.
     * @param lo The lower index.
     * @param hi The higher index.
     * @return Is a[lo...hi] sorted?
     */
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }
}
