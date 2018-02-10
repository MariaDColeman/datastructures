public class SquareCounter implements Counter {
    private int value;
    public SquareCounter() {
        this.value = 2;
    }
    public void up() {
        this.value = (int) Math.pow(this.value, 2);
    }
    public void down() {
        this.value = (int) Math.ceil(Math.sqrt(this.value));
    }
    public int value() {
        return this.value;
    }
    public static void main(String[] args) {
        Counter c = new SquareCounter();
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
    }
}
