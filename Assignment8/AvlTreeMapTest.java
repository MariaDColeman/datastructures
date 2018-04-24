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
public class AvlTreeMapTest extends OrderedMapTestBase {
    final static int SIZE = 5;
    final static Integer INIT = 1;

    @Override
    OrderedMap<Integer, Integer> createUnit() {
        return new AvlTreeMap<>();
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

}
