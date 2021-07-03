import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.MaxPQ;

// Integer numbers only
public class DynamicMedian {
    MaxPQ<Integer> maxPQ; // for smaller numbers
    MinPQ<Integer> minPQ; // for bigger numbers

    public DynamicMedian() {
        maxPQ = new MaxPQ<>();
        minPQ = new MinPQ<>();
    }

    public static void main(String[] args) {
        int[] testNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        DynamicMedian test = new DynamicMedian();

        for (int testNumber : testNumbers) {
            test.insert(testNumber);
            System.out.println(test.median());
        }
    }

    public void insert(int number) {
        if (maxPQ.isEmpty() && minPQ.isEmpty()) {
            minPQ.insert(number);
        } else if (number > minPQ.min()) {
            minPQ.insert(number);
            balance();
        } else {
            maxPQ.insert(number);
            balance();
        }
    }

    public void balance() {
        while (maxPQ.size() - minPQ.size() >= 2) {
            minPQ.insert(maxPQ.delMax());
        }
        while (minPQ.size() - maxPQ.size() >= 2) {
            maxPQ.insert(minPQ.delMin());
        }
    }

    public Double median() {
        if ((maxPQ.size() + minPQ.size()) % 2 == 0) {
            return (maxPQ.max() + minPQ.min()) / 2.0;
        } else {
            return minPQ.min() * 1.0;
        }
    }
}
