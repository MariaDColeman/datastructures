/**
 * Implementation of a FlexibleCounter.
 * Maria Coleman
 * mcolem31
 */
public class FlexibleCounter implements ResetableCounter {
    private int value;
    private int delta;
    private int initial;

    /**
     * Constructor for FlexibleCounter.
     * @param start the starting value.
     * @param delta the amount to change by.
     * @throws InitializeException if start &lt; 0 or delta &le; 0.
     */
    public FlexibleCounter(int start, int delta) throws InitializeException {
        if (start >= 0 && delta > 0) {
            this.value = start;
            this.delta = delta;
            this.initial = start;
        } else {
            throw new InitializeException();
        }
    }

    /**
     * Imcrement this counter up.
     */
    public void up() {
        this.value = this.value + this.delta;
    }

    /**
     * Increment this counter down.
     */
    public void down() {
        this.value = this.value - this.delta;
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
        this.value = this.initial;
    }

    /**
     * Main method used for testing of FlexibleCounter.
     * @param args not used.
     */
    public static void main(String[] args) {
        ResetableCounter c = new FlexibleCounter(2, 5);
        assert c.value() == 2;
        c.up();
        assert c.value() == 7;
        c.down();
        assert c.value() == 2;
        c.down();
        assert c.value() == -3;
        c.down();
        assert c.value() == -8;
        c.up();
        assert c.value() == -3;
        c.reset();
        assert c.value() == 2;
        c.up();
        assert c.value() == 7;
        c.down();
        assert c.value() == 2;
        c.reset();
        assert c.value() == 2;
        c.down();
        assert c.value() == -3;
        //exceptions
        try {
            ResetableCounter c1 = new FlexibleCounter(-1, 5);
            assert false;
        } catch (InitializeException e) {
            //the exception was correctly caught
        }
        try {
            ResetableCounter c2 = new FlexibleCounter(2, 0);
            assert false;
        } catch (InitializeException e) {
            //the exception was correctly caught
        }
    }
}
