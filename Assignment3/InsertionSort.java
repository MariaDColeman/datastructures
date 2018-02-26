/**
 * Maria Coleman
 * mcolem31
 */

/**
 * An insertion sorting algorithm.
 *
 * I adapted the example code from the Intro to Java course
 * examples at www.cs.jhu.edu/~joanne/cs107/code/Assrays/CustomerDbase.java
 * and the provided GnomeSort.java helper methods
 *
 * @param <T> Element type.
 */
public final class InsertionSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

    // Helper to make code more readable.
    private boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

      

    @Override
    public void sort(Array<T> a) {

        T temp;
        for (int i = 1; i < a.length(); i++) {
            for (int j = i; j > 0; j--) {
                if (this.less(a.get(j), a.get(j-1))) {
                    temp = a.get(j);
                    a.put(j, a.get(j-1));
                    a.put((j-1), temp);
                }
            }

            
        }


    }

    @Override
    public String name() {
        return "Insertion Sort";
    }
}
