/*
 * Maria Coleman
 * mcolem31
 */

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

}
