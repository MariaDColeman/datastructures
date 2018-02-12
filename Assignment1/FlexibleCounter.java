public class FlexibleCounter implements ResetableCounter {
    private int value;
    private int delta;
    private int initial;
    public FlexibleCounter(int start, int delta) {
        assert start >= 0;
        assert delta > 0;
        this.value = start;
        this.delta = delta;
        this.initial = start;
    }
    public void up() {
        this.value = this.value + this.delta;
    }
    public void down() {
        this.value = this.value - this.delta;
    }
    public int value() {
        return this.value;
    }
    public void reset() {
        this.value = this.initial;
    }
    public static void main(String[] args) {
        ResetableCounter c = new FlexibleCounter(2,5);
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
    }
}
