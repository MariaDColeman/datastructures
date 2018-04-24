/*
 * Maria Coleman
 * mcolem31
 */

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Random;
import java.util.ArrayList;

/** Instantiate the class to test. */
public class BinarySearchTreeMapTest extends OrderedMapTestBase {
    final static int SIZE = 97;
    final static Integer INIT = 1;

    @Override
    OrderedMap<Integer, Integer> createUnit() {
        return new BinarySearchTreeMap<>();
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

//        Random rand = new Random();
//        int n = rand.nextInt(97) + 1;
        // the eqn i use means -100 is the lowest possible
//        unit.insert(-100, INIT);
        
        for (int i = 0; i < SIZE; i++) {
//            Random rand = new Random();
           // int n = rand.nextInt(97) + 1;
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

    @Test
    public void testToString() {
        StringBuilder s = new StringBuilder();
        s.append("{");
        for (int i = -1; i < SIZE-1; i++) {
            s.append(i).append(": 1, ");
        }
        s.setLength(s.length()-2);
        s.append("}");
        String ex = s.toString();
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
            unit.insert(ra[i], INIT);
        }
        assertEquals(ex, unit.toString());
    }

}
