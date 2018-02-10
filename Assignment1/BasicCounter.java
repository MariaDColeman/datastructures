/**
 * Implementation of a BasicCounter.
 */
public class BasicCounter implements Counter {
    private int value;
    public void up() {
        this.value++;
    }
    public void down() {
        this.value--;
    }
    public int value() {
        return this.value;
    }
    public static void main(String[] args) {
        Counter c = new BasicCounter();
        assert c.value() == 0;
        c.up();
        assert c.value() == 1;
        c.down();
        assert c.value() == 0;
        c.down();
        assert c.value() == -1;
    }
}
