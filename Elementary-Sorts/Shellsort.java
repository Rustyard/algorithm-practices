// just an example, not complete
public class Shellsort {
    public static void sort(Comparable[] a) {
        int N = a.length;

        int h = 1;

        while (h < N / 3) h = 3 * h + 1; // 1,4,13,40,121,364,...  (the 3x+1 increment sequence)

        while (h >= 1) {
            // h-sort the array
            // (insertion sort with increment h)
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h)
                    exchange(a, j, j - h);
            }
            h /= 3;
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exchange(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) return false;
        }
        return true;
    }
}
