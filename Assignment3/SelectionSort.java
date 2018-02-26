/**
 * Maria Coleman
 * mcolem31
 */

/**
 * A selection sorting algorithm.
 *
 * I adapted the example code Sort2.java from the Intro to Java course
 * examples at www.cs.jhu.edu/~joanne/cs107/code/Assrays/Sort2.java
 * and the provided GnomeSort.java helper methods
 *
 * @param <T> Element type.
 */
public final class SelectionSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

    // Helper to make code more readable.
    private boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

    // Helper to make code more readable.
    private boolean greater(T a, T b) {
        return a.compareTo(b) > 0;
    }

    // Helper to make code more readable
    private void swap(Array<T> a, int i, int j) {
        T t = a.get(i);
        a.put(i, a.get(j));
        a.put(j, t);
    }   

    @Override
    public void sort(Array<T> a) {

        int pass;
        int index;
        int min_index;
        T minvalue;
        
        for (pass = 0; pass < a.length() - 1; pass++) {
            //initialize min
            min_index = pass;
            minvalue = a.get(pass);

            //find next min
            for (index = pass + 1; index < a.length(); index++) {
                if (this.greater(a.get(min_index), a.get(index))) {
                    min_index = index;
                    minvalue = a.get(min_index);
                } //end if
            } //end find next min

        // move min to front with swap
        this.swap(a, min_index, pass);
        

        } //end for pass

    }

    @Override
    public String name() {
        return "Selection Sort";
    }
}
