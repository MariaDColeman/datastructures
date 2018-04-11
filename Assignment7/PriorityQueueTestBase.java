/*
 * Maria Coleman
 * mcolem31
 */

/*
 * Testing multiple Priority Queue implementations.
 *
 * All our Priority Queue implementations (SortedArrayPriorityQueue,
 * BinaryHeapPriorityQueue) are
 * supposed to behave in the same way: they all implement (explicitly) the
 * PriorityQueue interface.
 *
 */
import java.util.Comparator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Iterator;

public abstract class PriorityQueueTestBase {
    final static int SIZE = 97;

    Comparator<Integer> cmp = new Comparator<Integer>() {
        @Override
        public int compare(Integer a, Integer b) {
            return b.compareTo(a);
        }
    };

    // Unit under test. That's why it's called a "unit test" after all.
    PriorityQueue<Integer> unit;
    PriorityQueue<Integer> unit2;

    // The method that will instantiate the actual object we run our tests on.
    // One way to think about this is as a "virtual constructor," a method that
    // makes whatever instance is appropriate. Note how it has exactly the same
    // signature as the constructor we want all Set implementations to share.
    abstract PriorityQueue<Integer> createUnit();

    abstract PriorityQueue<Integer> createUnit(Comparator<Integer> cmp);

    // Run before each @Test below in order to get a fresh unit.
    @Before
    public void setupPriorityQueue() {
        unit = this.createUnit();
        unit2 = this.createUnit(cmp);
    }


//    @Test
//    public void testWithMax() {
//        unit = this.createUnit(cmp);
//        assertTrue(unit.empty());
//    }

    // Test expected behavior

    @Test
    public void newIsEmpty() {
        assertTrue(unit.empty());
    }

    @Test
    public void newInsertMakesNotEmpty() {
        assertTrue(unit.empty());
        unit.insert(1);
        assertTrue(!unit.empty());
    }

    @Test
    public void newInsertMakesNotEmptyLots() {
        assertTrue(unit.empty());
	for (int i = 0; i < SIZE; i++) {
            unit.insert(i);
            assertTrue(!unit.empty());
        }
    }

    @Test
    public void insertIncreasesSize() {
        int i = 0;
        unit.insert(0);
        while (!unit.empty()) {
            Integer t = unit.remove();
            i++;
        }
        assertEquals(1, i);
    }

    @Test
    public void insertIncreasesSizeLots() {
        int i = 0;
        for (int j = 0; j < SIZE; j++) {
            unit.insert(j);
        }
        while (!unit.empty()) {
            Integer t = unit.remove();
           
            assertEquals(new Integer(i), t);
            i++;
        }
        assertEquals(SIZE, i);
    }


    @Test
    public void duplicateInsertIncreaseSize() {
        int i = 0;
        unit.insert(0);
        unit.insert(0);
        while (!unit.empty()) {
            Integer t = unit.remove();
            i++;
        }
        assertEquals(2, i);
    }

    

    @Test
    public void thereRemoveDecreasesSize() {
        for (int j = 0; j < SIZE; j++) {
            unit.insert(j);
        }
        int i = SIZE;
        while (!unit.empty()) {
            Integer t = unit.remove();
            i--;
        }
        assertEquals(0, i);
    }


    @Test
    public void bestWhenHas() {
        unit.insert(1);
        Integer t = unit.best();
        assertEquals(new Integer(1), t);
    }

    @Test
    public void bestLots() {
        for (int j = 0; j < SIZE; j++) {
            unit.insert(j);
        }
        int i = 0;
        while (!unit.empty()) {
            Integer t = unit.best();
            assertEquals(new Integer(i), t);
            unit.remove();
            i++;
        }
    }





    // test exception behavior
    @Test(expected=EmptyException.class)
    public void removeOnEmpty() {
        Integer t = unit.remove();
    }

    @Test(expected=EmptyException.class)
    public void insertThenRemoveOnEmpty() {
        unit.insert(1);
        Integer t = unit.remove();
        assertTrue(unit.empty());
        Integer t2 = unit.remove();
    }

    @Test(expected=EmptyException.class)
    public void bestOnEmpty() {
        Integer t = unit.best();
    }

    @Test(expected=EmptyException.class)
    public void insertThenRemoveThenBestOnEmpty() {
        unit.insert(1);
        Integer t = unit.remove();
        assertTrue(unit.empty());
        Integer t2 = unit.best();
    }


    // Test expected behavior with max element comparator

    @Test
    public void newIsEmptyMAX() {
        assertTrue(unit2.empty());
    }

    @Test
    public void newInsertMakesNotEmptyMAX() {
        assertTrue(unit2.empty());
        unit2.insert(1);
        assertTrue(!unit2.empty());
    }

    @Test
    public void newInsertMakesNotEmptyLotsMAX() {
        assertTrue(unit2.empty());
        for (int i = 0; i < SIZE; i++) {
            unit2.insert(i);
            assertTrue(!unit2.empty());
        }
    }

    @Test
    public void insertIncreasesSizeMAX() {
        int i = 0;
        unit2.insert(0);
        while (!unit2.empty()) {
            Integer t = unit2.remove();
            i++;
        }
        assertEquals(1, i);
    }

    @Test
    public void insertIncreasesSizeLotsMAX() {
        int i = 0;
        for (int j = 0; j < SIZE; j++) {
            unit2.insert(j);
        }
        while (!unit2.empty()) {
            Integer t = unit2.remove();
            assertEquals(new Integer(SIZE-1-i), t);
            i++;
        }
        assertEquals(SIZE, i);
    }

    @Test
    public void duplicateInsertIncreaseSizeMAX() {
        int i = 0;
        unit2.insert(0);
        unit2.insert(0);
        while (!unit2.empty()) {
            Integer t = unit2.remove();
            i++;
        }
        assertEquals(2, i);
    }



    @Test
    public void thereRemoveDecreasesSizeMAX() {
        for (int j = 0; j < SIZE; j++) {
            unit2.insert(j);
        }
        int i = SIZE;
        while (!unit2.empty()) {
            Integer t = unit2.remove();
            i--;
        }
        assertEquals(0, i);
    }

    @Test
    public void bestWhenHasMAX() {
        unit2.insert(1);
        Integer t = unit2.best();
        assertEquals(new Integer(1), t);
    }

    @Test
    public void bestLotsMAX() {
        for (int j = 0; j < SIZE; j++) {
            unit2.insert(j);
        }
        int i = 0;
        while (!unit2.empty()) {
            Integer t = unit2.best();
            assertEquals(new Integer(SIZE-1-i), t);
            unit2.remove();
            i++;
        }
    }

    @Test(expected=EmptyException.class)
    public void removeOnEmptyMAX() {
        Integer t = unit2.remove();
    }

    @Test(expected=EmptyException.class)
    public void insertThenRemoveOnEmptyMAX() {
        unit2.insert(1);
        Integer t = unit2.remove();
        assertTrue(unit2.empty());
        Integer t2 = unit2.remove();
    }

    @Test(expected=EmptyException.class)
    public void bestOnEmptyMAX() {
        Integer t = unit2.best();
    }

    @Test(expected=EmptyException.class)
    public void insertThenRemoveThenBestOnEmptyMAX() {
        unit2.insert(1);
        Integer t = unit2.remove();
        assertTrue(unit2.empty());
        Integer t2 = unit2.best();
    }


}
