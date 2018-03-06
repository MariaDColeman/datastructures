/**
 * Stack implemented using a linked list.
 *
 * All operations take O(1) time in the worst case; however each push()
 * results in a new object being allocated which may be inappropriate
 * for some applications.
 *
 * @param <T> Element type.
 */
public class ListStack<T> implements Stack<T> {
    private static final class Node<T> {
        Node<T> next;
        T data;
    }

    private Node<T> top;

    /**
     * Create an empty stack.
     */
    public ListStack() {
    }

    @Override
    public T top() throws EmptyException {
        try {
            return this.top.data;
        } catch (NullPointerException e) {
            throw new EmptyException();
        }
    }

    @Override
    public void pop() throws EmptyException {
        try {
            this.top = this.top.next;
        } catch (NullPointerException e) {
            throw new EmptyException();
        }
    }

    @Override
    public void push(T t) {
        Node<T> n = new Node<T>();
        n.data = t;
        n.next = this.top;
        this.top = n;
    }

    @Override
    public boolean empty() {
        return this.top == null;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        for (Node<T> n = this.top; n != null; n = n.next) {
            s.append(n.data);
            if (n.next != null) {
                s.append(", ");
            }
        }
        s.append("]");
        return s.toString();
    }
}
