import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array implementation using a basic Java array.
 *
 * The obvious implementation of the Array interface. Absolutely, positively
 * nothing fancy going on here.
 *
 * There are two reasons for this class to exist: First it's an example for the
 * style of code we're about to write a lot of. Second it's actually useful
 * because generics don't play well with basic arrays in Java. So we'll end up
 * using SimpleArray whenever basic Java arrays would give us generic grief.
 *
 * @param <T> Element type.
 */
public class SimpleArray<T> implements Array<T> {
    // These really shouldn't be here but checkstyle requires them here... :-/
    private static final int TEST_LENGTH = 10;
    private static final String TEST_INITIAL = "Peter";
    private static final String TEST_PUT_LENGTH = "Paul";
    private static final String TEST_PUT_GET = "Mary";

    // The obvious representation of our abstract Array.
    private T[] data;

    /**
     * Create a new SimpleArray.
     *
     * @param n Length of array, must be &gt; 0.
     * @param t Initial value to store in each slot.
     * @throws LengthException if n &le; 0.
     */
    public SimpleArray(int n, T t) throws LengthException {
        if (n <= 0) {
            throw new LengthException();
        }

        // This cast works around Java's problems with generic arrays. The
        // resulting warning is acceptable because there simply is no better
        // way of doing this.
        this.data = (T[]) new Object[n];

        // Array slots are null by default, no need to initialize twice.
        if (t == null) {
            return;
        }

        // Slightly faster than running our own loop.
        Arrays.fill(this.data, t);
    }

    // If we let ArrayIndexOutOfBoundsException propagate, we leak an
    // implementation detail we should probably hide. (Also that name is so
    // horrible, it deserves to live in a dark cave in Mordor.)

    @Override
    public T get(int i) throws IndexException {
        try {
            return this.data[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexException();
        }
    }

    @Override
    public void put(int i, T t) throws IndexException {
        try {
            this.data[i] = t;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexException();
        }
    }

    @Override
    public int length() {
        return this.data.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (T t: this.data) {
            sb.append(t).append(", ");
        }
        sb.setLength(sb.length() - 2); // Remove the last ", " we put in.
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    // An iterator to traverse the array from front to back so we can use our
    // class with for-each loops just like we can basic Java arrays. Using an
    // inner class (not a nested class) allows us to access the outer object's
    // "data" member without any stunts.
    private final class ArrayIterator implements Iterator<T> {
        // Current position in the basic Java array.
        int current;

        @Override
        public boolean hasNext() {
            return this.current < SimpleArray.this.data.length;
        }

        @Override
        public T next() throws NoSuchElementException {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            T t = SimpleArray.this.data[this.current];
            this.current += 1;
            return t;
        }
    }

    /**
     * Simple assert-based unit test.
     *
     * Make sure you run this with -enableassertions! We'll learn a much better
     * approach to unit testing later.
     *
     * @param args Command line arguments (ignored).
     */
    public static void main(String[] args) {
        // create the "unit under test"
        Array<String> a = new SimpleArray<>(TEST_LENGTH, TEST_INITIAL);

        // test cases derived from axioms
        testNewLength(a);
        testNewGet(a);
        testPutLength(a);
        testPutGet(a);

        // test cases for additional Java methods
        testToString();
        testIterator();

        // test cases for exception conditions
        testExceptionNew();
        testExceptionGet(a);
        testExceptionPut(a);
    }

    // Axiom 1
    private static void testNewLength(Array<String> a) {
        assert a.length() == TEST_LENGTH;
    }

    // Axiom 2
    private static void testNewGet(Array<String> a) {
        for (int i = 0; i < TEST_LENGTH; i++) {
            assert a.get(i).equals(TEST_INITIAL);
        }
    }

    // Axiom 3
    private static void testPutLength(Array<String> a) {
        // axiom 3
        for (int i = 0; i < TEST_LENGTH; i++) {
            a.put(i, TEST_PUT_LENGTH);
            assert a.length() == TEST_LENGTH;
        }

    }

    // Axiom 4-ish, a stricter, more complete test is possible
    private static void testPutGet(Array<String> a) {
        for (int j = 0; j < TEST_LENGTH; j++) {
            a.put(j, TEST_PUT_GET);
            assert a.get(j).equals(TEST_PUT_GET);

            for (int i = j + 1; i < TEST_LENGTH; i++) {
                assert a.get(i).equals(TEST_PUT_LENGTH);
            }
        }
    }

    // specialized, smaller test case to avoid typing too much
    private static void testToString() {
        Array<String> a = new SimpleArray<>(2, TEST_INITIAL);
        String expected = "[" + TEST_INITIAL + ", " + TEST_INITIAL + "]";
        assert a.toString().equals(expected);
    }

    private static void testIterator() {
        Array<String> a = new SimpleArray<>(3, TEST_INITIAL);
        String expected = TEST_INITIAL + TEST_INITIAL + TEST_INITIAL;
        String result = "";
        for (String s: a) {
            result += s;
        }
        assert result.equals(expected);
    }

    private static void testExceptionNew() {
        try {
            Array<String> a = new SimpleArray<>(-1, TEST_INITIAL);
            assert false;
        } catch (LengthException e) {
            // test passed
        }

        try {
            Array<String> a = new SimpleArray<>(0, TEST_INITIAL);
            assert false;
        } catch (LengthException e) {
            // test passed
        }
    }

    private static void testExceptionGet(Array<String> a) {
        try {
            a.get(-1);
            assert false;
        } catch (IndexException e) {
            // test passed
        }

        try {
            a.get(TEST_LENGTH);
            assert false;
        } catch (IndexException e) {
            // test passed
        }
    }

    private static void testExceptionPut(Array<String> a) {
        try {
            a.put(-1, TEST_INITIAL);
            assert false;
        } catch (IndexException e) {
            // test passed
        }

        try {
            a.put(TEST_LENGTH, TEST_INITIAL);
            assert false;
        } catch (IndexException e) {
            // test passed
        }
    }
}
