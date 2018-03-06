/** Instantiate the class to test. */
public class SparseArrayTest extends ArrayTestBase {
    @Override
    Array<String> createUnit(int length, String initial) {
        return new SparseArray<>(length, initial);
    }
}
