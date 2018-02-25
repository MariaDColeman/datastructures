/**
 * The essence of any sorting algorithm.
 *
 * Disclaimer: This is not a "typical" use of interfaces in Java. But it is
 * a convenient way to write code that treats any number of sorting algorithms
 * on arrays in a uniform way. (Idealists might even call it an "abuse" of
 * polymorphism; but let's side with the pragmatists in this case.)
 *
 * In order to sort, some random type T is not good enough. Whatever we want
 * to sort needs a <b>total order</b> and we need a way to compare things for
 * "less than" (or "greater than") as well as for "equals". This is why we use
 * a <b>bounded</b> type parameter below: We need a T that's <b>at least</b> of
 * type Comparable! That ensures that we have a compareTo() method which gives
 * us exactly that.
 *
 * See https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html for
 * all the details.
 *
 * @param <T> Element type.
 */
public interface SortingAlgorithm<T extends Comparable<T>> {
    /**
     * Sort an array.
     *
     * The array is modified "in place" through a sequence of get(), put(), and
     * length() operations.
     *
     * @param a Array to sort.
     */
    void sort(Array<T> a);

    /**
     * Name of algorithm.
     *
     * Simply a convenience method, useful when we print out the results for
     * various algorithms.
     *
     * @return Name of algorithm.
     */
    String name();
}
