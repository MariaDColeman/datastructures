import java.util.ArrayList; // see note in main() below

/**
 * Simple polymorphic test framework for arrays.
 *
 * See last week's PolyCount. You need to add the actual test cases but we
 * already gave you a fairly complete set in SimpleArray's main method. So it's
 * really mostly about putting existing pieces together and adapting them
 * slightly. Instead of String array we're using Integer arrays here, and
 * instead of a relatively small array we use a long-ish one. Enjoy!
 *
 * This code is incomplete and needs some serious work. Don't wait until the
 * last minute to attack this part of the assignment, it'll take you a while.
 */
public final class PolyArray {
    // don't change these values!
    private static final int LENGTH = 113;
    private static final int INITIAL = 7;
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


    // specialized, smaller test case to avoid typing too much.
    private static void testToString() {
    
    }


    // methods for testing additional Java methods go here

    // methods for testing preconditions go here; note that you'll have to
    // make your own instances for testing the "new" preconditions

    /**
     * Run (mostly polymorphic) tests on various array implementations.
     *
     * Make sure you run this with -enableassertions!
     *
     * @param args Command line arguments (ignored).
     */
    public static void main(String[] args) {
        // For various technical reasons, we cannot use a plain Java array
        // here like we did in PolyCount. Sorry.
        ArrayList<Array<Integer>> arrays = new ArrayList<>();
        arrays.add(new SimpleArray<Integer>(LENGTH, INITIAL));
        //arrays.add(new ListArray<Integer>(LENGTH, INITIAL));
        //arrays.add(new SparseArray<Integer>(LENGTH, INITIAL));

        // Test all the axioms. We can do that nicely in a loop. In the test
        // methods, keep in mind that you are handed the same object over and
        // over again!
        for (Array<Integer> a: arrays) {
            // call your axiom and Java test cases here (hint: order matters)
            

            //test cases derived from axioms
            testNewLength(a);
            testNewGet(a);
            testPutLength(a);
            testPutGet(a);

            // test cases for additional java methods
            testToString();
            testIterator();

        }

        // Test all the preconditions. Sadly we have to code each one of these
        // out manually, not even Java's reflection API would help...
    }
}
