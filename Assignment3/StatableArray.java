// Maria Coleman
// mcolem31

/**
 * Implementation of Statable that extends SimpleArray.
 *
 * @param <T> Element type.
 */

public class StatableArray<T> extends SimpleArray<T> implements Statable {
    //these are up here just for checkstyle
    //they're for testing
    private static final int TEST_LENGTH = 4;
    private static final String TEST_INIT = "Maria";

    private int numReads;
    private int numWrites;

    /**
     * Create a new StatableArray.
     *
     * @param n length of array, must be &gt; 0.
     * @param t Initial value to store in each slot.
     * @throws LengthException if n &le; 0.
     */
    public StatableArray(int n, T t) throws LengthException {
        super(n, t);
    }

    @Override
    public void resetStatistics() {
        this.numReads = 0;
        this.numWrites = 0;
        return;
    }

    @Override
    public int numberOfReads() {
        return this.numReads;
    }

    @Override
    public int numberOfWrites() {
        return this.numWrites;
    }

    @Override
    public T get(int i) throws IndexException {
        this.numReads++;
        return super.get(i);
    }

    @Override
    public void put(int i, T t) throws IndexException {
        this.numWrites++;
        super.put(i, t);
    }

    @Override
    public int length() {
        this.numReads++;
        return super.length();
    }

    /**
     * Simple assert-based unit tests.
     *
     * Make sure you run with -enableassertions
     *
     * @param args Command line arguments (ignored)
     */
    public static void main(String[] args) {
        //create the "unit under test"
        StatableArray<String> a = new StatableArray<>(TEST_LENGTH, TEST_INIT);

        assert a.numberOfReads() == 0;
        assert a.numberOfWrites() == 0;

        a.length();
        assert a.numberOfReads() == 1;
        assert a.numberOfWrites() == 0;

        a.get(0);
        assert a.numberOfReads() == 2;
        assert a.numberOfWrites() == 0;

        a.put(0, "Justin");
        assert a.numberOfWrites() == 1;
        assert a.numberOfReads() == 2;

        try {
            a.get(-1);
        } catch (IndexException e) {
            //IndexException successfully thrown, make sure read count did
            //change and write count didnt because accoridng to Tim Kutcher's
            //piazza note, it doesnt matter if the operation worked or not
            //(a get still causes the num of reads count to increase even if
            //the index is out of bounds
            assert a.numberOfReads() == 3;
            assert a.numberOfWrites() == 1;
        }

        try {
            a.put(-1, "Wrong");
        } catch (IndexException e) {
            //IndexException successfully thrown, make sure write count did
            //change but read count didnt. see comment above
            assert a.numberOfReads() == 3;
            assert a.numberOfWrites() == 2;
        }

        a.resetStatistics();
        assert a.numberOfWrites() == 0;
        assert a.numberOfReads() == 0;
    }
}
