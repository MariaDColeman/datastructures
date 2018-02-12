/**
 * Implementation of a BasicCounter.
 */
public class BasicCounter implements ResetableCounter {
    private int value;

    /**
     * Incrememnt this counter up.
     */
    public void up() {
        this.value++;
    }

    /**
     * Increment this counter down.
     */
    public void down() {
        this.value--;
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
        this.value = 0;
    }

    /**
     * Main method used for testing of BasicCounter.
     * @param args not used.
     */
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
