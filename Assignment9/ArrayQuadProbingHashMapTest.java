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
public class ArrayQuadProbingHashMapTest extends MapTestBase {
    final static int SIZE = 97;
    final static Integer INIT = 1;

    @Override
    Map<Integer, Integer> createUnit() {
        return new ArrayQuadProbingHashMap<>();
    }


}
