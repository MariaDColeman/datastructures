public class ArrayDeque<T> implements Deque<T> {

    private SimpleArray<T> array;
    private int f = 0;
    private int b = 0;
    private int counter = 0;


    public ArrayDeque() {
        this.array = new SimpleArray(1, null);        
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
    public T front() {
        //return this.data[0];
        return this.array.get(this.f);
    }

    @Override
    public T back() {
        //return this.data[0];
        return this.array.get(this.b);

    }

    @Override
    public void insertFront(T t) {
        if (this.counter == this.array.length()) {
            this.ifFull();
        }
        this.f = (this.f - 1 + this.array.length()) % this.array.length();
        this.array.put(this.f, t);
        this.counter++;
    }

    private void ifFull() {
            SimpleArray<T> temp = new SimpleArray(this.array.length() * 2, null);
            int index = 0;
            for (int i = this.f; i != this.b; i = (i + 1) % this.array.length()) {
                temp.put(index, this.array.get(i));
                index++;
            }
            this.f = 0;
            this.b = this.array.length() - 1;
            this.array = temp;
    }

    @Override
    public void insertBack(T t) {
        if (this.counter == this.array.length()) {
            this.ifFull();
        }

        this.b = (this.b + 1) % this.array.length();
        this.array.put(this.b, t);
        this.counter++;
    }

    @Override
    public void removeFront() {
        this.f = (this.f + 1) % this.array.length();
        this.counter--;
    }

    @Override
    public void removeBack() {
        this.b = (this.b - 1 + this.array.length()) % this.array.length();
        this.counter--;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = this.f; i != this.b; i = (i + 1) % this.array.length()) {

            sb.append(this.array.get(i)).append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("]");
        return sb.toString();

    }

}
