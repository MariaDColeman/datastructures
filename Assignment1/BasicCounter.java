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
}
