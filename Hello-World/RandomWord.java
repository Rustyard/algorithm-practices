import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String championString = null;
        double i = 1.0;
        while (!StdIn.isEmpty()) {
            if (StdRandom.bernoulli(1 / i)) {
                championString = StdIn.readString();
            }
            else {
                StdIn.readString();
            }
            i++;
        }
        StdOut.println(championString);
    }
}
