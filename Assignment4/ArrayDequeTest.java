/** Instantiate the class to test. */
public class ArrayDequeTest extends DequeTestBase {
    @Override
    Deque<String> createUnit() {
        return new ArrayDeque<>();
    }
}
