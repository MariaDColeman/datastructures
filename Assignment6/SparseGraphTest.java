/*
 * Maria Coleman
 * mcolem31
 */

/** Instantiate the class to test. */
public class SparseGraphTest extends GraphTestBase {
    @Override
    Graph<String, Integer> createUnit() {
        return new SparseGraph<>();
    }
}
