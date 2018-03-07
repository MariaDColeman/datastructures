/*
 * Maria Coleman
 * mcolem31
 */

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class DequeTestBase {
    final static String INITIAL = "Maria";
    final static int LENGTH = 9;
    static String expectedToString;

    // Unit under test
    Deque<String> unit;

    // The method that will instantiate the actual object we run our tests on.
    abstract Deque<String> createUnit();

    // Run before each @Test below in order to get a fresh unit.
    @Before
    public void setupDeque() {
        unit = this.createUnit();
    }


    @Test
    public void newIsEmpty() {
        assertTrue(unit.empty());
    }

    @Test
    public void newHasProperLength() {
        assertEquals(0, unit.length());
    }

    @Test
    public void testInsertFront() {
        unit.insertFront("A");
        unit.insertFront("B");
        unit.insertFront("C");
        assertEquals(3, unit.length());
        assertEquals("C", unit.front());
    }

    @Test
    public void testInsertBack() {
        unit.insertBack("A");
       // System.out.println("A:");
       // System.out.println(unit.toString());
        unit.insertBack("B");
       // System.out.println("B:");
       // System.out.println(unit.toString());
        unit.insertBack("C");
       // System.out.println(unit.toString());
        assertEquals(3, unit.length());
        assertEquals("A", unit.front());
        assertEquals("C", unit.back());

    }

    @Test
    public void testRemoveFront() {
        unit.insertFront("A");
        unit.insertFront("B");
        unit.insertFront("C");
        assertEquals(3, unit.length());
        assertEquals("C", unit.front());
        assertEquals("A", unit.back());
        unit.removeFront();
        assertEquals(2, unit.length());
        assertEquals("B", unit.front());
        assertEquals("A", unit.back());
        unit.removeFront();
        assertEquals(1, unit.length());
        assertEquals("A", unit.front());
        assertEquals("A", unit.back());
        unit.removeFront();
        assertTrue(unit.empty());
    }

    @Test
    public void testRemoveBack() {
        unit.insertFront("A");
        unit.insertFront("B");
        unit.insertFront("C");
        assertEquals(3, unit.length());
        assertEquals("C", unit.front());
        assertEquals("A", unit.back());
        unit.removeBack();
        assertEquals(2, unit.length());
        assertEquals("C", unit.front());
        assertEquals("B", unit.back());
        unit.removeBack();
        assertEquals(1, unit.length());
        assertEquals("C", unit.front());
        assertEquals("C", unit.back());
        unit.removeBack();
        assertTrue(unit.empty());
    }

    @Test
    public void testInsertRemoveFront() {
        unit.insertFront("A");
        assertEquals("A", unit.front());
        assertEquals("A", unit.back());
        assertEquals(1, unit.length());
        assertTrue(!unit.empty());
        unit.removeFront();
        assertTrue(unit.empty());
        assertEquals(0, unit.length());
    }

    @Test
    public void testInsertRemoveBack() {
        unit.insertBack("A");
        assertEquals("A", unit.front());
        assertEquals("A", unit.back());
        assertEquals(1, unit.length());
        assertTrue(!unit.empty());
        unit.removeBack();
        assertTrue(unit.empty());
        assertEquals(0, unit.length());
    }

    @Test
    public void testInsertFrontRemoveBack() {
        unit.insertFront("A");
        assertEquals("A", unit.front());
        assertEquals("A", unit.back());
        assertEquals(1, unit.length());
        assertTrue(!unit.empty());
        unit.removeBack();
        assertTrue(unit.empty());
        assertEquals(0, unit.length());
    }

    @Test
    public void testInsertBackRemoveFront() {
        unit.insertBack("A");
        assertEquals("A", unit.front());
        assertEquals("A", unit.back());
        assertEquals(1, unit.length());
        assertTrue(!unit.empty());
        unit.removeFront();
        assertTrue(unit.empty());
        assertEquals(0, unit.length());
    }

    @Test
    public void testInsertFrontLots() {
        for (int i = 0; i < 20; i++) {
            unit.insertFront("F");
            assertEquals((i+1), unit.length());
        }
        for (int i = 0; i < 20; i++) {
            assertEquals("F", unit.front());
            unit.removeFront();
        }
    }

    @Test
    public void testInsertBackLots() {
        for (int i = 0; i < 20; i++) {
            unit.insertFront("B");
            assertEquals((i+1), unit.length());
        }
        for (int i = 0; i < 20; i++) {
            assertEquals("B", unit.back());
            unit.removeBack();
        }

    }


    // Test exception behavior

    @Test(expected=EmptyException.class)
    public void frontOnNewEmpty() {
        unit.front();
    }

    @Test(expected=EmptyException.class)
    public void backOnNewEmpty() {
         unit.back();
    }

    @Test(expected=EmptyException.class)
    public void removeFrontOnNewEmpty() {
        unit.removeFront();
    }

    @Test(expected=EmptyException.class)
    public void removeBackOnNewEmpty() {
         unit.removeBack();
    }

    @Test(expected=EmptyException.class)
    public void frontOnEmpty() {
        unit.insertFront("t");
        unit.removeFront();
        unit.front();
    }

    @Test(expected=EmptyException.class)
    public void backOnEmpty() {
        unit.insertBack("t");
        unit.removeBack();
        unit.back();
    }

    @Test(expected=EmptyException.class)
    public void removeFrontOnEmpty() {
        unit.insertFront("t");
        unit.removeFront();
        unit.removeFront();
    }

    @Test(expected=EmptyException.class)
    public void removeBackOnEmpty() {
        unit.insertBack("t");
        unit.removeBack();
        unit.removeBack();
    }


    // Test Java-specific methods / behaviors.

    // Run this once, before anything else, to create the expected string
    @BeforeClass
    public static void setupToStringExpected() {
        expectedToString = "[";
        for (int i = 0; i < LENGTH - 1; i++) {
            expectedToString += INITIAL + ", ";
        }
        expectedToString += INITIAL + "]";
        //System.out.println(expectedToString);
    }

    @Test
    public void dequeToString() {
        for (int i = 0; i < LENGTH; i++) {
            unit.insertFront(INITIAL);
        }
        //System.out.println(unit.toString());
        assertEquals(expectedToString, unit.toString());
    }

    @Test
    public void emptyToString() {
        String expected = "[]";
        assertEquals(expected, unit.toString());
    }
}
