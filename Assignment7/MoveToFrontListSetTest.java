import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/** Instantiate the class to test. */
public class MoveToFrontListSetTest extends SetTestBase {
    @Override
    Set<Integer> createUnit() {
        return new MoveToFrontListSet<>();
    }

    @Test
    public void setIteratorValues() {
        for (int i = 0; i < SIZE; i++) {
            unit.insert(i+i);
        }

        int j = 0;
        for (Integer i: unit) {
            assertEquals(new Integer((SIZE-1-j)*2), i);
            j += 1;
        }
    }


}
