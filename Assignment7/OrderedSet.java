/**
 * Sets of ordered values.
 * Iteration order is ascending.
 * @param <T> Element type.
 */
public interface OrderedSet<T extends Comparable<? super T>> extends Set<T> {
    // We just need additional constraints on T, operations are the same.
}
