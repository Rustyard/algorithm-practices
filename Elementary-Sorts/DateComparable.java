public class DateComparable implements Comparable<DateComparable> {
    private int year, month, date;

    @Override
    public int compareTo(DateComparable that) {
        if (this.year > that.year) return 1;
        if (this.year < that.year) return -1;
        if (this.month > that.month) return 1;
        if (this.month < that.month) return -1;
        if (this.date > that.date) return 1;
        if (this.date < that.date) return -1;
        return 0;
    }
}
