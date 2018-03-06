public class ArrayDeque<T> implements Deque<T> {

    private T[] data;

    @Override
    public boolean empty() {
        return false;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public T front() {
        return this.data[0];
    }

    @Override
    public T back() {
        return this.data[0];

    }

    @Override
    public void insertFront(T t) {

    }

    @Override
    public void insertBack(T t) {

    }

    @Override
    public void removeFront() {

    }

    @Override
    public void removeBack() {

    }

}
