/*
 * Maria Coleman
 * mcolem31
 */

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Iterator;
import java.util.ArrayList;
/**
 * Testing multiple Map implementations.
 *
 * All our OrderedMap implementations (BinarySearchTreeMap, AvlTreeMap,
 * TreapMap) are
 * supposed to behave in the same way: they all implement (explicitly) the
 * OrderedMap interface.
 *
 */
public abstract class MapTestBase {
    final static int SIZE = 97;
    final static Integer INIT = 1;

    // Unit under test. That's why it's called a "unit test" after all.
    Map<Integer, Integer> unit;

    // The method that will instantiate the actual object we run our tests on.
    // One way to think about this is as a "virtual constructor," a method that
    // makes whatever instance is appropriate. Note how it has exactly the same
    // signature as the constructor we want all Set implementations to share.
    abstract Map<Integer, Integer> createUnit();

    // Run before each @Test below in order to get a fresh unit.
    @Before
    public void setupList() {
        unit = this.createUnit();
    }

    // Test expected behavior

    @Test
    public void newHasProperSize() {
//        System.out.println(unit.toString());
        assertEquals(0, unit.size());
    }

    @Test
    public void newInsertIncreasesSize() {
        assertEquals(0, unit.size());
        unit.insert(1, INIT);
        assertEquals(1, unit.size());
    }

    @Test
    public void newInsertIncreasesSizeLots() {
        assertEquals(0, unit.size());
	for (int i = 0; i < SIZE; i++) {
            unit.insert(i, INIT);
            assertEquals(i+1, unit.size());
        }
//        System.out.println(unit.toString());

    }

//    @Test
//    public void duplicateInsertPreservesSize() {
//        assertEquals(0, unit.size());
//        unit.insert(1);
//        assertEquals(1, unit.size());
//        unit.insert(1);
//        assertEquals(1, unit.size());
//    }

    @Test
    public void thereRemoveDecreasesSize() {
        assertEquals(0, unit.size());
        unit.insert(1, INIT);
        assertEquals(1, unit.size());
        Integer valueRemoved = unit.remove(1);
        assertEquals(0, unit.size());
        assertEquals(INIT, valueRemoved);
    }

    @Test
    public void thereRemoveDecreasesSizeLots() {
        for (int i = 0; i < SIZE; i++) {
            unit.insert(i, INIT);
//            assertTrue(unit.has(i));

        }
        assertEquals(SIZE, unit.size());
          //  assertTrue(unit.has(90));

        for (int i = SIZE - 1; i >= 0; i--) {
//            System.out.println(i);
//           System.out.println(unit.size());
//           System.out.println(unit.toString());
           //System.out.println(unit.size());
//            assertTrue(unit.has(i));
            unit.remove(i);
  //         System.out.println(unit.size());

            assertEquals(i, unit.size());
        }
    }

//    @Test
//    public void missingRemovePreservesSize() {
//        unit.insert(1);
//        assertEquals(1, unit.size());
//        unit.remove(2);
//        assertEquals(1, unit.size());
//    }

    @Test
    public void hasWhenHas() {
        unit.insert(1, INIT);
        assertTrue(unit.has(1));
    }

    @Test
    public void hasWhenHasIsFalse() {
        unit.insert(1, INIT);
        assertTrue(!unit.has(2));
    }

    @Test
    public void hasFalseWhenNull() {
        assertTrue(!unit.has(null));
    }


    @Test
    public void hasWhenHasLots() {
        for (int i = 0; i < SIZE; i++) {
            unit.insert(i, INIT);
            assertTrue(unit.has(i));
        }
    }

    @Test
    public void hasWhenHasIsFalseAfterRemove() {
        unit.insert(1, INIT);
        assertTrue(unit.has(1));
        unit.remove(1);
        assertTrue(!unit.has(1));
    }



    // test exception behavior
    @Test(expected=IllegalArgumentException.class)
    public void removeOnEmpty() {
        Integer t = unit.remove(1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void insertThenRemoveOnEmpty() {
        //System.out.println("insertThenRemoveOnEmpty:");
        //System.out.println(unit.toString());
        unit.insert(1, INIT);
        //System.out.println(unit.toString());
        Integer t = unit.remove(1);
        //System.out.println(unit.toString());
        assertEquals(0, unit.size());
        Integer t2 = unit.remove(1);
    }

//    @Test(expected=IllegalArgumentException.class)
//    public void hasOnEmpty() {
//        unit.has(1);
//    }

//    @Test(expected=IllegalArgumentException.class)
//    public void insertThenRemoveThenHasOnEmpty() {
//        unit.insert(1, INIT);
//        Integer t = unit.remove(1);
//        assertEquals(0, unit.size());
//        unit.has(1);
//    }

    @Test(expected=IllegalArgumentException.class)
    public void getOnEmpty() {
        Integer t = unit.get(1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getOnNull() {
        Integer t = unit.get(null);
    }


    @Test(expected=IllegalArgumentException.class)
    public void insertThenRemoveThenGetOnEmpty() {
        unit.insert(1, INIT);
        Integer t = unit.remove(1);
        assertEquals(0, unit.size());
        Integer t2 = unit.get(1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void putOnNull() {
        unit.put(null, INIT);
    }

    @Test(expected=IllegalArgumentException.class)
    public void putOnNotMapped() {
        unit.put(1, INIT);
    }


    // Test Java-specific methods / behaviors.


    @Test
    public void setIteratorLength() {
        for (int i = 0; i < SIZE; i++) {
            unit.insert(i+i, INIT);
        }
        int j = 0;
        for (Integer i: unit) {
            j += 1;
        }
        assertEquals(SIZE, j);
    }

    @Test
    public void testEmptyIterator() {
        Iterator<Integer> i = unit.iterator();
        assertEquals(false, i.hasNext());
    }

    @Test
    public void testIteratorSingleton() {
        unit.insert(4, INIT);
        Iterator<Integer> i = unit.iterator();
        assertEquals(true, i.hasNext());
        Integer myInt = i.next();
        assertEquals(new Integer(4), myInt);
        assertEquals(false, i.hasNext());
    }


 



}
