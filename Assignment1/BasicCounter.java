/**
 * Implementation of a BasicCounter.
 */
public class BasicCounter implements ResetableCounter {
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
    public void reset() {
        this.value = 0;
    }
    public static void main(String[] args) {
        ResetableCounter c = new BasicCounter();
        assert c.value() == 0;
        c.up();
        assert c.value() == 1;
        c.down();
        assert c.value() == 0;
        c.down();
        assert c.value() == -1;
        c.reset();
        assert c.value() == 0;
        c.up();
        assert c.value() == 1;
        c.reset();
        assert c.value() == 0;
        c.down();
        assert c.value() == -1;
    }
}
