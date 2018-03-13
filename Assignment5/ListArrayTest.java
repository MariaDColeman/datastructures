/** Instantiate the class to test. */
public class ListArrayTest extends ArrayTestBase {
    @Override
    Array<String> createUnit(int length, String initial) {
        return new ListArray<>(length, initial);
    }
}
