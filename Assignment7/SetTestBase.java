/*
 * Maria Coleman
 * mcolem31
 */

/*
 * Testing multiple Set implementations.
 *
 * All our Set implementations (ListSet, ArraySet, MoveToFrontListSet,
 * TransposeArraySet) are
 * supposed to behave in the same way: they all implement (explicitly) the
 * Set interface.
 *
 */

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Iterator;

public abstract class SetTestBase {
    final static int SIZE = 97;

    // Unit under test. That's why it's called a "unit test" after all.
    Set<Integer> unit;

    // The method that will instantiate the actual object we run our tests on.
    // One way to think about this is as a "virtual constructor," a method that
    // makes whatever instance is appropriate. Note how it has exactly the same
    // signature as the constructor we want all Set implementations to share.
    abstract Set<Integer> createUnit();

    // Run before each @Test below in order to get a fresh unit.
    @Before
    public void setupList() {
        unit = this.createUnit();
    }


 //   abstract void setIteratorValues();


    // Test expected behavior

    @Test
    public void newHasProperSize() {
        assertEquals(0, unit.size());
    }

    @Test
    public void newInsertIncreasesSize() {
        assertEquals(0, unit.size());
        unit.insert(1);
        assertEquals(1, unit.size());
    }

    @Test
    public void newInsertIncreasesSizeLots() {
        assertEquals(0, unit.size());
	for (int i = 0; i < SIZE; i++) {
            unit.insert(i);
            assertEquals(i+1, unit.size());
        }
    }

    @Test
    public void duplicateInsertPreservesSize() {
        assertEquals(0, unit.size());
        unit.insert(1);
        assertEquals(1, unit.size());
        unit.insert(1);
        assertEquals(1, unit.size());
    }

    @Test
    public void thereRemoveDecreasesSize() {
        assertEquals(0, unit.size());
        unit.insert(1);
        assertEquals(1, unit.size());
        unit.remove(1);
        assertEquals(0, unit.size());
    }

    @Test
    public void thereRemoveDecreasesSizeLots() {
        for (int i = 0; i < SIZE; i++) {
            unit.insert(i);
        }
        assertEquals(SIZE, unit.size());
        for (int i = SIZE - 1; i >= 0; i--) {
            unit.remove(i);
            assertEquals(i, unit.size());
        }
    }

    @Test
    public void missingRemovePreservesSize() {
        unit.insert(1);
        assertEquals(1, unit.size());
        unit.remove(2);
        assertEquals(1, unit.size());
    }

    @Test
    public void hasWhenHas() {
        unit.insert(1);
        assertTrue(unit.has(1));
    }

    @Test
    public void hasWhenHasIsFalse() {
        unit.insert(1);
        assertTrue(!unit.has(2));
    }

    @Test
    public void hasWhenHasLots() {
        for (int i = 0; i < SIZE; i++) {
            unit.insert(i);
            assertTrue(unit.has(i));
        }
    }

    @Test
    public void hasWhenHasIsFalseAfterRemove() {
        unit.insert(1);
        assertTrue(unit.has(1));
        unit.remove(1);
        assertTrue(!unit.has(1));
    }





    // Test Java-specific methods / behaviors.


    @Test
    public void setIteratorLength() {
        for (int i = 0; i < SIZE; i++) {
            unit.insert(i+i);
        }
        int j = 0;
        for (Integer i: unit) {
            j += 1;
        }
        assertEquals(SIZE, j);
    }

//    @Test
//    public void setIteratorValues() {
//        for (int i = 0; i < SIZE; i++) {
//            unit.insert(i+i);
//        }

//        int j = 0;
//        for (Integer i: unit) {
//            assertEquals(new Integer((SIZE-1-j)*2), i);
//            j += 1;
//        }
//    }

    @Test
    public void testEmptyIterator() {
        Iterator<Integer> i = unit.iterator();
        assertEquals(false, i.hasNext());
    }

    @Test
    public void testIteratorSingleton() {
        unit.insert(4);
        Iterator<Integer> i = unit.iterator();
        assertEquals(true, i.hasNext());
        Integer myInt = i.next();
        assertEquals(new Integer(4), myInt);
        assertEquals(false, i.hasNext());
    }


}
