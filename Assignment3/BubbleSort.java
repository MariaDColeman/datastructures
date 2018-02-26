/**
 * Maria Coleman
 * mcolem31
 */

/**
 * A bubble sorting algorithm.
 *
 * I adapted the example code Sort2.java from the Intro to Java course
 * examples at www.cs.jhu.edu/~joanne/cs107/code/Assrays/Sort2.java
 * and the provided GnomeSort.java helper methods and the basicBubbleSort
 * in Problem 3
 *
 * @param <T> Element type.
 */
public final class BubbleSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

    // Helper to make code more readable.
    private boolean less(T a, T b) {
        return a.compareTo(b) < 0;
    }

    // Helper to make code more readable.
    private boolean greater(T a, T b) {
        return a.compareTo(b) > 0;
    }

    // Helper to make code more readable.
    private void swap(Array<T> a, int i, int j) {
        T t = a.get(i);
        a.put(i, a.get(j));
        a.put(j, t);
    }

    @Override
    public void sort(Array<T> a) {

        for (int size = a.length() - 1; size >= 1; size--) {

            for (int i = 0; i < size; i++) {

                if (this.greater(a.get(i), a.get(i+1))) {
                    //need to swap
                    this.swap(a, i, i+1);
                }

            }

        }        

    }

    @Override
    public String name() {
        return "Bubble Sort";
    }
}
