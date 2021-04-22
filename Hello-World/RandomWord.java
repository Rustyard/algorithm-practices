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
                // TODO: RandomWord.java:14: Calling 'StdIn.readString()' in more than one place suggests poor design in this program. [Design]
                StdIn.readString();
            }
            i++;
        }
        StdOut.println(championString);
    }
}
