/*
 * Testing multiple Array implementations.
 *
 * All our Array implementations (SimpleArray, ListArray, SparseArray) are
 * supposed to behave in the same way: they all implement (explicitly) the
 * Array interface and (implicitly) the Array ADT after all.
 *
 * We said that the purpose of unit testing is to check that a given piece of
 * code conforms to its specification, but the only complete JUnit4 test we've
 * seen so far was hard-coded for SimpleArray. It would be a Bad Idea to keep
 * going like this: We'd end up duplicating the test code again and again, once
 * to test ListArray, once to test SparseArray, etc.
 *
 * Duplication is the root of all evil (well, it is in programming anyway), so
 * let's do something different. Since the test cases come (for the most part)
 * from the ADT, the only real difference in JUnit4 drivers would be which
 * implementation ("unit under test") we instantiate. So let's collect all the
 * test cases into an ArrayTestBase class (the thing you're reading right now)
 * and also define an abstract operation to create an instance of some Array
 * implementation. Then we write derived test classes, one for each Array
 * implementation, that simply fill in this one method appropriately.
 *
 * The result is that we have all the test cases only once (base class), yet we
 * can test any number of implementations against the specification (one
 * derived class each). Total win! :-) As you read the code, you should pay
 * particular attention to how createUnit connects the general test code to
 * specific instances.
 */

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public abstract class ArrayTestBase {
    final static String INITIAL = "Peter";
    final static int LENGTH = 97;
    static String expectedToString;

    // Unit under test. That's why it's called a "unit test" after all.
    Array<String> unit;

    // The method that will instantiate the actual object we run our tests on.
    // One way to think about this is as a "virtual constructor," a method that
    // makes whatever instance is appropriate. Note how it has exactly the same
    // signature as the constructor we want all Array implementations to share.
    abstract Array<String> createUnit(int length, String initial);

    // Run before each @Test below in order to get a fresh unit.
    @Before
    public void setupArray() {
        unit = this.createUnit(LENGTH, INITIAL);
    }

    // Just a helper method to produce strings that depend on index.
    private String filler(int i) {
        return String.valueOf(i * i);
    }

    // Test axioms / expected behavior based on ADT Array.

    @Test
    public void newHasProperLength() {
        assertEquals(LENGTH, unit.length());
    }

    @Test
    public void newIsInitialized() {
        for (int i = 0; i < unit.length(); i++) {
            assertEquals(INITIAL, unit.get(i));
        }
    }

    @Test
    public void putPreservesLength() {
        assertEquals(LENGTH, unit.length());
        for (int i = 0; i < LENGTH; i++) {
            unit.put(i, filler(i));
            assertEquals(LENGTH, unit.length());
        }
    }

    @Test
    public void putModifiesGivenSlot() {
        for (int i = 0; i < LENGTH; i++) {
            unit.put(i, filler(i));
            assertEquals(filler(i), unit.get(i));
        }
    }

    @Test
    public void putPreservesOtherSlots() {
        for (int i = 0; i < LENGTH; i++) {
            unit.put(i, filler(i));
            // All slots <= i have been changed.
            for (int j = 0; j <= i; j++) {
                assertEquals(filler(j), unit.get(j));
            }
            // All slots > i have not been changed.
            for (int j = i + 1; j < LENGTH; j++) {
                assertEquals(INITIAL, unit.get(j));
            }
        }
    }

    // Test preconditions / exception behavior based on ADT Array.

    @Test(expected=LengthException.class)
    public void zeroLengthNew() {
        Array<String> a = createUnit(0, INITIAL);
    }

    @Test(expected=LengthException.class)
    public void negativeLengthNew() {
        Array<String> a = createUnit(-1, INITIAL);
    }

    @Test(expected=IndexException.class)
    public void lowIndexGet() {
        String x = unit.get(-1);
    }

    @Test(expected=IndexException.class)
    public void highIndexGet() {
        String x = unit.get(LENGTH);
    }

    @Test(expected=IndexException.class)
    public void lowIndexPut() {
        unit.put(-1, "Ouch");
    }

    @Test(expected=IndexException.class)
    public void highIndexPut() {
        unit.put(LENGTH, "Ouch");
    }

    // Test Java-specific methods / behaviors.

    // Run this once, before anything else, to create the expected string.
    @BeforeClass
    public static void setupToStringExpected() {
        expectedToString = "[";
        for (int i = 0; i < LENGTH - 1; i++) {
            expectedToString += INITIAL + ", ";
        }
        expectedToString += INITIAL + "]";
    }

    @Test
    public void arrayToString() {
        assertEquals(expectedToString, unit.toString());
    }

    @Test
    public void arrayIteratorLength() {
        int j = 0;
        for (String s: unit) {
            j += 1;
        }
        assertEquals(LENGTH, j);
    }

    @Test
    public void arrayIteratorValues() {
        for (int i = 0; i < LENGTH; i++) {
            unit.put(i, filler(i));
        }
        int j = 0;
        for (String s: unit) {
            assertEquals(filler(j), s);
            j += 1;
        }
    }
}
