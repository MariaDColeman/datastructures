/*
 * Maria Coleman
 * mcolem31
 */

/**
 * Implementation of a Deque using an array.
 * @param <T> element type
 */
public class ArrayDeque<T> implements Deque<T> {

    private SimpleArray<T> array;
    private int f;
    private int b;
    private int counter;

    /**
     * Create a new ArrayDeque with array length of 1 and initial value null.
     */
    public ArrayDeque() {
        this.array = new SimpleArray<>(1, null);
    }


    @Override
    public boolean empty() {
        return this.counter == 0;
    }

    @Override
    public int length() {
        return this.counter;
    }

    @Override
    public T front() throws EmptyException {
        //return this.data[0];
        if (this.empty()) {
            throw new EmptyException();
        }
        return this.array.get(this.f);
    }

    @Override
    public T back() throws EmptyException {
        //return this.data[0];
        if (this.empty()) {
            throw new EmptyException();
        }
        //return this.array.get(this.b);
        return this.array.get((this.b - 1 + this.array.length())
            % this.array.length());

    }

    @Override
    public void insertFront(T t) {
        if (this.counter == this.array.length()) {
            this.ifFull();
            //System.out.println("after doubled:" + this.array.toString());
        }
        this.f = (this.f - 1 + this.array.length()) % this.array.length();
        this.array.put(this.f, t);
        this.counter++;
    }

    private void ifFull() {
        SimpleArray<T> temp = new SimpleArray<>(this.array.length() * 2, null);
        int index = 0;
        for (int i = 0; i < this.counter; i++) {
            temp.put(index, (this.array.get((this.f + i)
                % this.array.length())));
            index++;
        }
        this.f = 0;
        this.b = this.array.length();
        this.array = temp;
    }

    @Override
    public void insertBack(T t) {
        if (this.counter == this.array.length()) {
            this.ifFull();
        }
        this.array.put(this.b, t);
        this.b = (this.b + 1) % this.array.length();
        this.counter++;
    }

    @Override
    public void removeFront() throws EmptyException {
        if (this.empty()) {
            throw new EmptyException();
        }
        this.f = (this.f + 1) % this.array.length();
        this.counter--;
    }

    @Override
    public void removeBack() throws EmptyException {
        if (this.empty()) {
            throw new EmptyException();
        }
        this.b = (this.b - 1 + this.array.length()) % this.array.length();
        this.counter--;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.counter; i++) {
            sb.append(this.array.get((this.f + i)
                % this.array.length())).append(", ");
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        //System.out.println(sb.toString());
        return sb.toString();
    }
}
