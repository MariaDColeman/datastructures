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
public class SimpleMapTest extends MapTestBase {
    final static int SIZE = 97;
    final static Integer INIT = 1;

    @Override
    Map<Integer, Integer> createUnit() {
        return new SimpleMap<>();
    }

    @Test
    public void setIteratorValues() {
        
        for (int i = 0; i < SIZE; i++) {
            unit.insert(i-1, INIT);

        }

        int j = 0;
        Integer prev = new Integer(-1);
        for (Integer i: unit) {
            assertTrue(prev.compareTo(i) <= 0);
            //assertEquals(new Integer(j+j), i);
            j += 1;
            prev = i;
        }
    }

    @Test
    public void testToStringKnownEx() {
        StringBuilder s = new StringBuilder();
        s.append("{");
        for (int i = 1; i < 8; i++) {
            s.append(i).append(": 1, ");
        }
        s.setLength(s.length()-2);
        s.append("}");
        String ex = s.toString();

//            System.out.println(unit.toString());
        unit.insert(1,INIT);
//            System.out.println(unit.toString());
        unit.insert(2,INIT);
//            System.out.println(unit.toString());
        unit.insert(3,INIT);
//            System.out.println(unit.toString());
        unit.insert(4,INIT);
//            System.out.println(unit.toString());
        unit.insert(5,INIT);
//            System.out.println(unit.toString());
        unit.insert(6,INIT);
//            System.out.println(unit.toString());
        unit.insert(7,INIT);
//            System.out.println(unit.toString());
        assertEquals(ex, unit.toString());
    }


}
