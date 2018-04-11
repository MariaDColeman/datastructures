import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Random;


/** Instantiate the class to test. */
public class SortedArraySetTest extends SetTestBase {
    final static int SIZE = 97;

    @Override
    Set<Integer> createUnit() {
        return new SortedArraySet<>();
    }

    @Test
    public void setIteratorValues() {
        Random rand = new Random();
//        int n = rand.nextInt(97) + 1;
        // the eqn i use means -100 is the lowest possible
        unit.insert(-100);
        for (int i = 0; i < SIZE-1; i++) {
//            Random rand = new Random();
            int n = rand.nextInt(97) + 1;
//            System.out.println(i+i-n);
            unit.insert(i+i-n);
        }

        int j = 0;
        Integer prev = new Integer(-100);
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
