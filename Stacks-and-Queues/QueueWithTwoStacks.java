// queue implemented using 2 linked stacks.. needs debug
public class QueueWithTwoStacks<Type> {
    private LinkedStack<Type> stack1 = new LinkedStack<>();
    private LinkedStack<Type> stack2 = new LinkedStack<>();

    public boolean isEmpty() {
        return (stack1.isEmpty() && stack2.isEmpty());
    }

    public void enqueue(Type item) {
        if (isEmpty()) {
            stack1.push(item);
        }
        else if (stack1.isEmpty()) {
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            stack1.push(item);
        }
        else {
            stack1.push(item);
        }
    }

    public Type dequeue() {
        if (isEmpty()) return null;
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

}
