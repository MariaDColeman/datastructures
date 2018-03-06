/** Instantiate the class to test. */
public class SimpleArrayTest extends ArrayTestBase {
    @Override
    Array<String> createUnit(int length, String initial) {
        return new SimpleArray<>(length, initial);
    }
}
