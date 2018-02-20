import java.util.ArrayList; // see note in main() below

/**
 * Simple polymorphic test framework for arrays.
 *
 * Maria Coleman
 * mcolem31
 *
 */
public final class PolyArray {
    // don't change these values!
    private static final int LENGTH = 113;
    private static final int INITIAL = 7;
    // values I added
    private static final int TEST_PUT_LENGTH = 1;
    private static final int TEST_PUT_GET = 2;

    private PolyArray() {}

    // methods for testing ADT axioms go here

    // Axiom 1
    private static void testNewLength(Array<Integer> a) {
        assert a.length() == LENGTH;
    }

    // Axiom 2
    private static void testNewGet(Array<Integer> a) {
        for (int i = 0; i < LENGTH; i++) {
            assert a.get(i).equals(INITIAL);
        }
    }

    //Axiom 3
    private static void testPutLength(Array<Integer> a) {
        // axiom 3
        for (int i = 0; i < LENGTH; i++) {
            a.put(i, TEST_PUT_LENGTH);
            assert a.length() == LENGTH;
        }
    }

    // Axiom 4
    private static void testPutGet(Array<Integer> a) {
        for (int j = 0; j < LENGTH; j++) {
            a.put(j, TEST_PUT_GET);
            assert a.get(j).equals(TEST_PUT_GET);

            for (int i = j + 1; i < LENGTH; i++) {
                assert a.get(i).equals(TEST_PUT_LENGTH);
            }
        }
    }

    //testing other Java methods

    private static void testToString(Array<Integer> a) {
        // use helper method to make the expected string
        String expected = makeExpected(INITIAL, LENGTH);
        assert a.toString().equals(expected);
    }

    private static void testIterator(Array<Integer> a) {
        // use helper method to make the expected string
        String expected = makeExpectedIterator(INITIAL, LENGTH);
        String result = "";
        for (int i: a) {
            result += i;
        }
        assert result.equals(expected);
    }

    private static String makeExpected(int initial, int length) {
        String expected = "[";
        for (int i = 0; i < length; i++) {
            expected += initial + ", ";
        }
        expected = expected.substring(0, expected.length() - 2);
        expected += "]";
        return expected;
    }

    private static String makeExpectedIterator(int initial, int length) {
        String expected = "";
        for (int i = 0; i < length; i++) {
            expected += initial;
        }
        return expected;
    }

    // have to test makeExpected!
    private static void testMakeExpected() {
        String test = makeExpected(INITIAL, 2);
        String expected = "[" + INITIAL + ", " + INITIAL + "]";
        assert test.equals(expected);
    }

    // have to test makeExpectedIterator!
    private static void testMakeExpectedIterator() {
        String test = makeExpectedIterator(INITIAL, 3);
        String expected = Integer.toString(INITIAL) + INITIAL + INITIAL;
        assert test.equals(expected);
    }

    // testing exceptions

    private static void testExceptionGet(Array<Integer> a) {
        try {
            a.get(-1);
            assert false;
        } catch (IndexException e) {
            // test passed
        }

        try {
            a.get(LENGTH);
            assert false;
        } catch (IndexException e) {
            // test passed
        }
    }

    private static void testExceptionPut(Array<Integer> a) {
        try {
            a.put(-1, INITIAL);
            assert false;
        } catch (IndexException e) {
            // test passed
        }

        try {
            a.put(LENGTH, INITIAL);
            assert false;
        } catch (IndexException e) {
            // test passed
        }
    }

    private static void testExceptionNewSimple() {
        try {
            Array<Integer> a = new SimpleArray<>(-1, INITIAL);
            assert false;
        } catch (LengthException e) {
            // test passed
        }
    }

    private static void testExceptionNewList() {
        try {
            Array<Integer> a = new ListArray<>(-1, INITIAL);
            assert false;
        } catch (LengthException e) {
            // test passed
        }
    }


    private static void testExceptionNewSparse() {
        try {
            Array<Integer> a = new SparseArray<>(-1, INITIAL);
            assert false;
        } catch (LengthException e) {
            // test passed
        }
    }

    // I call this because I want to test if remove works and
    // there is an assert in SparseArray.java that would fail if not.
    private static void testRemove() {
        Array<Integer> a = new SparseArray<>(LENGTH, INITIAL);
        //System.out.println(a.toString());
        a.put(4, 18);
        //System.out.println(a.toString());
        //a.put(4, INITIAL);
        //System.out.println(a.toString());
        a.put(0, 18);
        a.put(0, INITIAL);
        a.put(LENGTH - 1, 18);
        a.put(LENGTH - 1, INITIAL);
    }

    /**
     * Run (mostly polymorphic) tests on various array implementations.
     *
     * Make sure you run this with -enableassertions!
     *
     * @param args Command line arguments (ignored).
     */
    public static void main(String[] args) {

        //test makeExpected
        testMakeExpected();

        //test makeExpectedIterator
        testMakeExpectedIterator();

        // For various technical reasons, we cannot use a plain Java array
        // here like we did in PolyCount. Sorry.
        ArrayList<Array<Integer>> arrays = new ArrayList<>();
        arrays.add(new SimpleArray<Integer>(LENGTH, INITIAL));
        arrays.add(new ListArray<Integer>(LENGTH, INITIAL));
        arrays.add(new SparseArray<Integer>(LENGTH, INITIAL));

        // Test all the axioms. We can do that nicely in a loop. In the test
        // methods, keep in mind that you are handed the same object over and
        // over again!
        for (Array<Integer> a: arrays) {
            // do these first so they take the original array
            testToString(a);
            testIterator(a);

            // test cases derived from axioms
            testNewLength(a);
            testNewGet(a);
            testPutLength(a);
            testPutGet(a);

            // test exceptions for get and put
            testExceptionGet(a);
            testExceptionPut(a);
        }

        // Test all the preconditions. Sadly we have to code each one of these
        // out manually, not even Java's reflection API would help...
        testExceptionNewSimple();
        testExceptionNewList();
        testExceptionNewSparse();

        testRemove();
    }
}
