

public class StatableArray<T> extends SimpleArray<T> implements Statable {
    //these are up here just for checkstyle
    //they're for testing
    private static final int TEST_LENGTH = 4;
    private static final String TEST_INITIAL = "Maria";

    private int numReads;
    private int numWrites;

    public StatableArray(int n, T t) throws LengthException {
        super(n, t);
    }

    public void resetStatistics() {
        this.numReads = 0;
        this.numWrites = 0;
        return;
    }

    public int numberOfReads() {
        return this.numReads;
    }

    public int numberOfWrites() {
        return this.numWrites;
    }

    public T get(int i) throws IndexException {
        try {
            this.numReads++;
            return super.get(i);
        } catch (IndexException e) {
            this.numReads--; //put it back if get failed
            throw new IndexException();
        }
    }    

    public void put(int i, T t) throws IndexException {
            //maybe put in try catch block    
            super.put(i, t);
            this.numWrites++; //this line won't be called if an IndexException is thrown by super
    }

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
        //SimpleArray<String> a = new StatableArray<>(TEST_LENGTH, TEST_INITIAL);
        StatableArray<String> a = new StatableArray<>(TEST_LENGTH, TEST_INITIAL);


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
            //IndexException successfully thrown, make sure counts didn't change
            assert a.numberOfReads() == 2;
            assert a.numberOfWrites() == 1;
        }

        try {
            a.put(-1, "Wrong");
        } catch (IndexException e) {
            //IndexException successfully thrown, make sure counts didn't change
            assert a.numberOfReads() == 2;
            assert a.numberOfWrites() == 1;
        }

        a.resetStatistics();
        assert a.numberOfWrites() == 0;
        assert a.numberOfReads() == 0;
    }    

}
