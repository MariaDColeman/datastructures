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
/**
 * Testing multiple Graph implementations.
 *
 */
public abstract class GraphTestBase {

    // unit under test
    Graph<String, Integer> unit;

    // The method that will instantiate the actual object we run our tests on.
    abstract Graph<String, Integer> createUnit();

    // Run before each @Test below in order to get a fresh unit.
    @Before
    public void setupList() {
        unit = this.createUnit();
    }


//    @Test
//    public void testPrintOutput() {
//        Vertex<String> a = unit.insert("A");
//        Vertex<String> b = unit.insert("B");
//        unit.insert(a, b, 7);
//        System.out.println(unit.toString());
//    }

    @Test
    public void testIteratorNoSum() {
        Iterable<Vertex<String>> ver = unit.vertices();
        Iterator<Vertex<String>> it = ver.iterator();
        int sum = 0;
        while (it.hasNext()) {
            it.next();
            sum++;
        }
        assertEquals(0, sum);
    }

    @Test
    public void testInsertVertexOnce() {
        unit.insert("A");
        Iterable<Vertex<String>> ver = unit.vertices();
        Iterator<Vertex<String>> it = ver.iterator();
        int sum = 0;
        while (it.hasNext()) {
            it.next();
            sum++;
        }
        assertEquals(1, sum);
    }

    @Test
    public void testInsertVertexLots() {
        for (int i = 0; i < 30; i++) {
            unit.insert("A" + Integer.toString(i));
        }
        Iterable<Vertex<String>> ver = unit.vertices();
        Iterator<Vertex<String>> it = ver.iterator();
        int sum = 0;
        while (it.hasNext()) {
            Vertex<String> my = it.next();
            assertEquals("A" + Integer.toString(sum), my.get());
            sum++;
        }
        assertEquals(30, sum);
    }


    @Test
    public void testInsertRemoveVertexOnce() {
        Vertex<String> a = unit.insert("A");
        unit.remove(a);
        Iterable<Vertex<String>> ver = unit.vertices();
        Iterator<Vertex<String>> it = ver.iterator();
        int sum = 0;
        while (it.hasNext()) {
            it.next();
            sum++;
        }
        assertEquals(0, sum);
    }

    @Test
    public void testInsertEdgeOnce() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        unit.insert(a, b, 7);
        Iterable<Edge<Integer>> ed = unit.edges();
        Iterator<Edge<Integer>> it = ed.iterator();
        int sum = 0;
        while (it.hasNext()) {
            it.next();
            sum++;
        }
        assertEquals(1, sum);
        Iterable<Vertex<String>> ver = unit.vertices();
        Iterator<Vertex<String>> it2 = ver.iterator();
        int sum2 = 0;
        while (it2.hasNext()) {
            it2.next();
            sum2++;
        }
        assertEquals(2, sum2);
    }

    @Test
    public void testInsertRemoveEdgeOnce() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        unit.remove(myE);
        Iterable<Edge<Integer>> ed = unit.edges();
        Iterator<Edge<Integer>> it = ed.iterator();
        int sum = 0;
        while (it.hasNext()) {
            it.next();
            sum++;
        }
        assertEquals(0, sum);
        Iterable<Vertex<String>> ver = unit.vertices();
        Iterator<Vertex<String>> it2 = ver.iterator();
        int sum2 = 0;
        while (it2.hasNext()) {
            Vertex<String> myV = it2.next();
            sum2++;
        }
        assertEquals(2, sum2);
    }

    @Test
    public void testFrom() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Vertex<String> myFrom = unit.from(myE);
        assertEquals(a, myFrom);
    }

    @Test
    public void testTo() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Vertex<String> myTo = unit.to(myE);
        assertEquals(b, myTo);
    }

    @Test
    public void testOutgoing() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Vertex<String> c = unit.insert("C");
        Edge<Integer> myE2 = unit.insert(a, c, 5);
//        System.out.println(unit.from(myE));
        Iterable<Edge<Integer>> ed = unit.outgoing(a);
//        System.out.println(unit.from(myE));
        Iterator<Edge<Integer>> it = ed.iterator();
//        System.out.println(unit.from(myE));

        int sum = 0;
        while (it.hasNext()) {
            Edge<Integer> myEdge = it.next();
            sum++;
        }
        assertEquals(2, sum);
    }

    @Test
    public void testVerticesIterable() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Iterable<Vertex<String>> ver = unit.vertices();
        Iterator<Vertex<String>> it2 = ver.iterator();
        int sum2 = 0;
        int i = 0;
        while (it2.hasNext()) {
            Vertex<String> myV = it2.next();
            if (i == 0) {
                assertEquals(a.get(), myV.get());
            } else {
                assertEquals(b.get(), myV.get());
            }
            sum2++;
            i++;
        }
        assertEquals(2, sum2);
    }

    @Test
    public void testEdgesIterable() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Vertex<String> c = unit.insert("C");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Edge<Integer> myE2 = unit.insert(a, c, 5);
        Iterable<Edge<Integer>> ed = unit.edges();
        Iterator<Edge<Integer>> it2 = ed.iterator();
        int sum2 = 0;
        int i = 0;
        while (it2.hasNext()) {
            Edge<Integer> my = it2.next();
            if (i == 0) {
                assertEquals(my.get(), myE.get());
                assertEquals(unit.from(my).get(), a.get());
            } else {
                assertEquals(my.get(), myE2.get());
                assertEquals(unit.from(my).get(), a.get());
            }
            sum2++;
            i++;
        }
        assertEquals(2, sum2);
    }

    @Test
    public void testOutgoingIterable() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Vertex<String> c = unit.insert("C");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Edge<Integer> myE2 = unit.insert(a, c, 5);
        Iterable<Edge<Integer>> ed = unit.outgoing(a);
        Iterator<Edge<Integer>> it2 = ed.iterator();
        int sum2 = 0;
        int i = 0;
        while (it2.hasNext()) {
            Edge<Integer> my = it2.next();
            if (i == 0) {
                assertEquals(my.get(), myE.get());
                assertEquals(unit.from(my).get(), a.get());
            } else {
                assertEquals(my.get(), myE2.get());
                assertEquals(unit.from(my).get(), a.get());
            }
            sum2++;
            i++;
        }
        assertEquals(2, sum2);
    }

    @Test
    public void testIncomingIterable() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Vertex<String> c = unit.insert("C");
        Edge<Integer> myE = unit.insert(b, a, 7);
        Edge<Integer> myE2 = unit.insert(c, a, 5);
        Iterable<Edge<Integer>> ed = unit.incoming(a);
        Iterator<Edge<Integer>> it2 = ed.iterator();
        int sum2 = 0;
        int i = 0;
        while (it2.hasNext()) {
            Edge<Integer> my = it2.next();
            if (i == 0) {
                assertEquals(my.get(), myE.get());
                assertEquals(unit.to(my).get(), a.get());
            } else {
                assertEquals(my.get(), myE2.get());
                assertEquals(unit.to(my).get(), a.get());
            }
            sum2++;
            i++;
        }
        assertEquals(2, sum2);
    }




    @Test
    public void testToStringOutput() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        unit.insert(a, b, 7);
        String tester = unit.toString();
        String example = "digraph {\n  \"A\";\n  \"B\";\n  \"A\" -> \"B\" [label=\"7\"];\n}";
//        System.out.println(tester);
//        System.out.println(example);
        assertEquals(example, tester);
    }


    @Test
    public void testLabelVertex() {
        String expect = "test";
        Vertex<String> a = unit.insert("A");
        String toInsert = "test";
        unit.label(a, toInsert);
        assertEquals(expect, (String) unit.label(a));
    }

    @Test
    public void testLabelEdge() {
        String expect = "test";
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> e = unit.insert(a, b, 7);
        String toInsert = "test";
        unit.label(e, toInsert);
        assertEquals(expect, (String) unit.label(e));
    }


    @Test
    public void testClearLabels() {
        String expect = "test";
        String expect2 = "test2";
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> e = unit.insert(a, b, 7);
        String toInsert = "test";
        String toInsert2 = "test2";
        unit.label(a, toInsert);
        unit.label(e, toInsert2);
        assertEquals(expect, (String) unit.label(a));
        assertEquals(expect2, (String) unit.label(e));
        unit.clearLabels();
        assertEquals(null, unit.label(a));
        assertEquals(null, unit.label(e));
    }


    // test exception behavior

    @Test(expected=PositionException.class)
    public void fromVertexInvalid() {
        Vertex<String> b = unit.insert("B");
        Vertex<String> a = null;
        Edge<Integer> myE = unit.insert(a, b, 7);
    }

    @Test(expected=PositionException.class)
    public void toVertexInvalid() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = null;
        Edge<Integer> myE = unit.insert(a, b, 7);
    }

    @Test(expected=InsertionException.class)
    public void selfLoop() {
        Vertex<String> a = unit.insert("A");
        Edge<Integer> myE = unit.insert(a, a, 7);
    }

    @Test(expected=InsertionException.class)
    public void duplicateEdge() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Edge<Integer> myE2 = unit.insert(a, b, 5);
    }

    @Test(expected=PositionException.class)
    public void removeVertexInvalid() {
        Vertex<String> b = unit.insert("B");
        Vertex<String> a = null;
        unit.remove(a);
    }

    @Test(expected=RemovalException.class)
    public void removeWithIncidentEdges() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        unit.remove(a);
    }

    @Test(expected=PositionException.class)
    public void removeInvalidEdge() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Edge<Integer> my = null;
        unit.remove(my);
    }

    @Test(expected=PositionException.class)
    public void outgoingInvalidVertex() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Vertex<String> o = null;
        Iterable<Edge<Integer>> out = unit.outgoing(o);
    }

    @Test(expected=PositionException.class)
    public void incomingInvalidVertex() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Vertex<String> o = null;
        Iterable<Edge<Integer>> out = unit.incoming(o);
    }

    @Test(expected=PositionException.class)
    public void fromInvalidEdge() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Edge<Integer> o = null;
        Vertex<String> v = unit.from(o);
    }

    @Test(expected=PositionException.class)
    public void toInvalidEdge() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Edge<Integer> o = null;
        Vertex<String> v = unit.to(o);
    }

    @Test(expected=PositionException.class)
    public void fromInvalidEdgeRemoved() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
//        System.out.println(unit.toString());
        unit.remove(myE);
//        System.out.println(unit.toString());
        Vertex<String> v = unit.from(myE);
    }

    @Test(expected=PositionException.class)
    public void labelInvalidVertex() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = null;
        String toInsert = "test";
        unit.label(b, toInsert);
    }

    @Test(expected=PositionException.class)
    public void labelInvalidEdge() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Edge<Integer> my = null;
        String toInsert = "test";
        unit.label(my, toInsert);
    }

    @Test(expected=PositionException.class)
    public void retriveLabelInvalidVertex() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = null;
        String toInsert = "test";
        unit.label(a, toInsert);
        unit.label(b);
    }

    @Test(expected=PositionException.class)
    public void retriveLabelInvalidEdge() {
        Vertex<String> a = unit.insert("A");
        Vertex<String> b = unit.insert("B");
        Edge<Integer> myE = unit.insert(a, b, 7);
        Edge<Integer> my = null;
        String toInsert = "test";
        unit.label(myE, toInsert);
        unit.label(my);
    }

}
