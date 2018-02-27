/**
 * Maria Coleman
 * mcolem31
 */

/**
 * An insertion sorting algorithm.
 *
 * I adapted the example code from the basicInsertionSort function
 * provided on Piazza
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

    // helper to make code more readable.
    private boolean greater(T a, T b) {
        return a.compareTo(b) > 0;
    }

    @Override
    public void sort(Array<T> a) {


        for (int i = 1; i < a.length(); i++) {
            for (int j = i; j > 0 && this.greater(a.get(j - 1),
                a.get(j)); j--) {
                T t = a.get(j);
                a.put(j, a.get(j - 1));
                a.put((j - 1), t);
            }
        }


    }

    @Override
    public String name() {
        return "Insertion Sort";
    }
}
