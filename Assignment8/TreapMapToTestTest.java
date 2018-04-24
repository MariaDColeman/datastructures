import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Random;
import java.util.ArrayList;

/** Instantiate the class to test. */
public class TreapMapToTestTest extends OrderedMapTestBase {
    final static int SIZE = 5;
    final static Integer INIT = 1;

    @Override
    OrderedMap<Integer, Integer> createUnit() {
        return new TreapMapToTest<>();
    }

    @Test
    public void setIteratorValues() {

        ArrayList<Integer> tempList = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE-1; i++) {
            tempList.add(i);
        }
        Integer[] ra = new Integer[SIZE];
        ra[0]=-1;
        for (int c = 1; c < SIZE; c++) {
            ra[c] = tempList.remove((int)(Math.random() * tempList.size()));
        }


        for (int i = 0; i < SIZE; i++) {
//            System.out.println(i+i-n);
            unit.insert(ra[i], INIT);
        }

        int j = 0;
        Integer prev = new Integer(-1);
        for (Integer i: unit) {
//            System.out.println(prev);
//            System.out.println(i);
//            assertTrue(prev <= i);
            assertTrue(prev.compareTo(i) <= 0);
            //assertEquals(new Integer(j+j), i);
            j += 1;
            prev = i;
        }
    }

//    @Test
//    public void testSmallKnownToString() {
//        String ex1 = "{1: 1}";
//        unit.insert(1, INIT);
//        assertEquals(ex1, unit.toString());
//        String ex2 = "{1: 1, 2: 1}";
//        unit.insert(2, INIT);
//        assertEquals(ex2, unit.toString());
//        String ex3 = "{1: 1, 2: 1, 3: 1}";
//        unit.insert(3, INIT);
//        assertEquals(ex3, unit.toString());
//        String ex4 = "{0: 1, 1: 1, 2: 1, 3: 1}";
//        unit.insert(0, INIT);
//        assertEquals(ex4, unit.toString());
//        String ex5 = "{-1: 1, 0: 1, 1: 1, 2: 1, 3: 1}";
//        unit.insert(-1, INIT);
//        assertEquals(ex5, unit.toString());

//    }

//    @Test
//    public void testToString() {
//        StringBuilder s = new StringBuilder();
//        s.append("{");
//        for (int i = -1; i < SIZE-1; i++) {
//            s.append(i).append(": 1, ");
//        }
//        s.setLength(s.length()-2);
//        s.append("}");
//        String ex = s.toString();
//        ArrayList<Integer> tempList = new ArrayList<>(SIZE);
//        for (int i = 0; i < SIZE-1; i++) {
//            tempList.add(i);
//        }
//        Integer[] ra = new Integer[SIZE];
//        ra[0]=-1;
//        for (int c = 1; c < SIZE; c++) {
//            ra[c] = tempList.remove((int)(Math.random() * tempList.size()));
//        }

//        for (int i = 0; i < SIZE; i++) {
//            System.out.println(ra[i]);
//            unit.insert(ra[i], INIT);
//            assertTrue(unit.has(ra[i]));
//        }
//        System.out.println(ex);
//        System.out.println(unit.toString());
//        assertEquals(ex, unit.toString());


//    }

}
