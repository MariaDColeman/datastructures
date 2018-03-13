/*
 * Maria Coleman
 * mcolem31
 */

/*
 * Testing multiple List implementations.
 *
 * All our List implementations (LinkedList, SentinelList) are
 * supposed to behave in the same way: they all implement (explicitly) the
 * List interface and (implicitly) the List ADT after all which was not made.
 */
import java.util.Iterator;
import java.lang.Iterable;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class ListTestBase {
    final static String INITIAL = "Peter";
    final static int LENGTH = 97;
    static String expectedToString;

    // Unit under test. That's why it's called a "unit test" after all.
    List<String> unit;

    // The method that will instantiate the actual object we run our tests on.
    // One way to think about this is as a "virtual constructor," a method that
    // makes whatever instance is appropriate. Note how it has exactly the same
    // signature as the constructor we want all Array implementations to share.
    abstract List<String> createUnit();

    // Run before each @Test below in order to get a fresh unit.
    @Before
    public void setupList() {
        unit = this.createUnit();
    }

    // Just a helper method to produce strings that depend on index.
    private String filler(int i) {
        return String.valueOf(i * i);
    }

    // Test axioms / expected behavior

    @Test
    public void newHasProperLength() {
        assertEquals(0, unit.length());
    }


    @Test
    public void insertFrontCorrectLength() {
        assertEquals(0, unit.length());
        for (int i = 0; i < LENGTH; i++) {
            unit.insertFront(filler(i));
            assertEquals((i + 1), unit.length());
        }
    }

    @Test
    public void insertBackCorrectLength() {
        assertEquals(0, unit.length());
        for (int i = 0; i < LENGTH; i++) {
            unit.insertBack(filler(i));
            assertEquals((i + 1), unit.length());
        }
    }


    @Test
    public void insertFrontReturnsFront() {
        for (int i = 0; i < LENGTH; i++) {
            Position<String> p = unit.insertFront(filler(i));
            assertEquals(p, unit.front());
        }
    }

    @Test
    public void insertBackReturnsBack() {
        for (int i = 0; i < LENGTH; i++) {
            Position<String> p = unit.insertBack(filler(i));
            assertEquals(p, unit.back());
        }
    }

    @Test
    public void insertFrontAndFirst() {
        for (int i = 0; i < LENGTH; i++) {
            Position<String> p = unit.insertFront(filler(i));
            assertTrue(unit.first(p));
        }
    }



    // Test preconditions / exception behavior based on ADT Array.

    @Test(expected=EmptyException.class)
    public void frontOnEmpty() {
        unit.front();
    }

    @Test(expected=EmptyException.class)
    public void backOnEmpty() {
        unit.back();
    }


    @Test(expected=EmptyException.class)
    public void removeFrontOnEmpty() {
        unit.removeFront();
    }

    @Test(expected=EmptyException.class)
    public void removeBackOnEmpty() {
        unit.removeBack();
    }

    @Test(expected=PositionException.class)
    public void nextOnLast() {
        Position<String> p = unit.insertFront(INITIAL);
        unit.next(p);
    }

    @Test(expected=PositionException.class)
    public void prevOnFirst() {
        Position<String> p = unit.insertFront(INITIAL);
        unit.previous(p);
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
    public void listToString() {
        for (int i = 0; i < LENGTH; i++) {
            unit.insertFront(INITIAL);
        }
        assertEquals(expectedToString, unit.toString());
    }

    @Test
    public void listIteratorLength() {
        int j = 0;
        for (int i = 0; i < LENGTH; i++) {
            unit.insertBack(INITIAL);
        }
        for (String s: unit) {
            j += 1;
        }
        assertEquals(LENGTH, j);
    }

    @Test
    public void listIteratorValues() {
        for (int i = 0; i < LENGTH; i++) {
            unit.insertBack(filler(i));
        }
        int j = 0;
        for (String s: unit) {
            assertEquals(filler(j), s);
            j += 1;
        }
    }

    @Test
    public void listIteratorValuesForward() {
        for (int i = 0; i < LENGTH; i++) {
            unit.insertBack(filler(i));
        }
        int j = 0;
        for (Iterator<String> iter = unit.forward(); iter.hasNext();) {
            assertEquals(filler(j), iter.next());
            j += 1;
        }
    }

    @Test
    public void listIteratorValuesBackward() {
        for (int i = 0; i < LENGTH; i++) {
            unit.insertBack(filler(i));
        }
        int j = LENGTH - 1;
        for (Iterator<String> iter = unit.backward(); iter.hasNext();) {
            assertEquals(filler(j), iter.next());
            j -= 1;
        }
    }


}
