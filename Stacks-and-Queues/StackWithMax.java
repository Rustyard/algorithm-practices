// stack with ReturnTheMaximum() implemented with linked stack.. needs debug
public class StackWithMax extends LinkedStack<Integer>{
    private Node first = null;
    private LinkedStack<Integer> stackMaxNumber = new LinkedStack<>();

    private class Node {
        Integer item;
        Node next;
    }

    @Override
    public void push(Integer item) {
        if (stackMaxNumber.isEmpty()) {
            stackMaxNumber.push(item);
        }
        else {
            Integer tempMax = stackMaxNumber.pop();
            stackMaxNumber.push(tempMax);
            if (item >= tempMax) { // be careful not to miss the "=" here (int values only)
                stackMaxNumber.push(item);
            }
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
    }

    @Override
    public Integer pop() {
        if (isEmpty()) return null;
        Integer tempMax = stackMaxNumber.pop();
        Integer item = first.item;

        if (!item.equals(tempMax)) {
            stackMaxNumber.push(tempMax); // push the number back
        }

        first = first.next;
        return item;
    }

    public Integer ReturnTheMaximum() {
        Integer tempMax = stackMaxNumber.pop();
        stackMaxNumber.push(tempMax);
        return tempMax;
    }
}
