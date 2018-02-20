/**
 * Arrays with integer positions.
 *
 * The constructor (which we cannot specify in an interface in Java) should
 * take a length &gt; 0 as well as an initial value to "plaster" all over the
 * new array. The constructor should throw LengthException if length &le; 0.
 *
 *   Array(int length, T initial) throws LengthException;
 *
 * The intuition behind an Array "a" with length "n" and initial value "t" is
 * best summarized by the following figure:
 *
 * <pre>
 *       0   1   2   3  ... n-2 n-1
 *     +---+---+---+---+---+---+---+
 *  a: | t | t | t | t |...| t | t |
 *     +---+-|-+---+---+---+-^-+---+
 *     \_____|______ n ______|_____/
 *           | get       put |
 *           v               |
 * </pre>
 *
 * Arrays have a fixed length that determines the valid index range. Arrays
 * hold values of arbitrary type; get() reads the value at a given index,
 * put() writes the value at a given index. Array iterators produce values
 * in ascending index order (from 0 to n-1).
 *
 * @param <T> Element type.
 */
public interface Array<T> extends Iterable<T> {
    /**
     * Write value at index.
     *
     * @param i Index to write value at.
     * @param t Value to write at index.
     * @throws IndexException if i &lt; 0 or i &gt; length-1.
     */
    void put(int i, T t) throws IndexException;

    /**
     * Read value at index.
     *
     * @param i Index to read value at.
     * @return Value read at index.
     * @throws IndexException if i &lt; 0 or i &gt; length-1.
     */
    T get(int i) throws IndexException;

    /**
     * Length of array.
     *
     * @return Length of array, always &gt; 0.
     */
    int length();
}
