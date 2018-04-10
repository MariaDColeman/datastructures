/**
 * Queue of ordered values.
 *
 * Priority queues are queues for which the <b>value</b> of an element
 * determines when it will become the "front" of the queue; in regular
 * queues only the order of insertion matters (first-in-first-out).
 *
 * The boarding process used by Southwest Airlines is an example of a
 * priority queue: Each passenger is assigned a unique number and all
 * passengers board strictly by number; when a passenger arrives does
 * not matter, they simply join the queue at the appropriate position
 * (potentially cutting in front of other passengers).
 *
 * The literature often distinguishes "minimum priority queues" (with
 * operations insert, minimum, extract-minimum) from "maximum priority
 * queues" (with operations insert, maximum, extract-maximum). Writing
 * and maintaining two versions of each implementation that only differ
 * in whether they use &le; or &ge; seems rather ill-advised.
 *
 * Addressing this problem first requires that we pick "more neutral"
 * method names. We chose insert, best, and remove. Of course "remove"
 * should be understood as "remove the best" here.
 *
 * As a second step we have to make the notion of "order" configurable,
 * a task we delegate to implementations of the interface. They must
 * provide <b>two</b> constructors: one that uses the "natural" ordering
 * of java.util.Comparable, and another that accepts an explicit
 * java.util.Comparator argument. (It's probably a good idea to take
 * the time and read the documentation of those interfaces.)
 *
 * With these things in place, we can now define "best" to mean "the
 * minimum value according to the queue's comparator" and voila: two
 * priority queues for the code of one.
 *
 * Please remember that a queue is not a set! If you insert some value
 * X three times, you have to remove X three times before it's gone.
 *
 * @param <T> Element type.
 */
public interface PriorityQueue<T extends Comparable<? super T>> {
    /**
     * Insert a value.
     * @param t Value to insert.
     */
    void insert(T t);

    /**
     * Remove best value.
     * @return Best value we just removed.
     * @throws EmptyException If queue is empty.
     */
    T remove() throws EmptyException;

    /**
     * Return best value.
     * @return Best value in the queue.
     * @throws EmptyException If queue is empty.
     */
    T best() throws EmptyException;

    /**
     * No elements?
     * @return True if queue is empty, false otherwise.
     */
    boolean empty();
}
