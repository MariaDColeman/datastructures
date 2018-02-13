/**
 * Implementation of a SquareCounter.
 * Maria Coleman
 * mcolem31
 */
public class SquareCounter implements ResetableCounter {
    private int value;

    /**
     * Constructor for SquareCounter.
     * The initial value is equal to 2.
     */
    public SquareCounter() {
        this.value = 2;
    }

    /**
     * Increment this counter up.
     */
    public void up() {
        this.value = (int) Math.pow(this.value, 2);
    }

    /**
     * Increment this counter down.
     */
    public void down() {
        this.value = (int) Math.ceil(Math.sqrt(this.value));
    }

    /**
     * Current value of this counter.
     * @return Current value.
     */
    public int value() {
        return this.value;
    }

    /**
     * Reset this counter.
     */
    public void reset() {
        this.value = 2;
    }

    /**
     * Main method used for testing of SquareCounter.
     * @param args not used.
     */
    public static void main(String[] args) {
        ResetableCounter c = new SquareCounter();
        assert c.value() == 2;
        c.up();
        assert c.value() == 4;
        c.down();
        assert c.value() == 2;
        c.down();
        assert c.value() == 2;
        c.up();
        assert c.value() == 4;
        c.up();
        assert c.value() == 16;
        c.reset();
        assert c.value() == 2;
        c.up();
        assert c.value() == 4;
        c.down();
        assert c.value() == 2;
        c.reset();
        assert c.value() == 2;
        c.down();
        assert c.value() == 2;
    }
}
