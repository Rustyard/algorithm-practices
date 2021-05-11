import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int printNum = Integer.parseInt(args[0]);
        RandomizedQueue<String> stringRandomizedQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String tempString = StdIn.readString();
            stringRandomizedQueue.enqueue(tempString);
        }

        for (int i = 0; i < printNum; i++) {
            StdOut.println(stringRandomizedQueue.dequeue());
        }
    }
}
