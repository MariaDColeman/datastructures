import java.util.Scanner;

/**
 * Filter unique integers from standard input to standard output.
 *
 * This version finally looks right: Using the appropriate data structure we
 * only have to write code that actually deals with the problem at hand.
 *
 * If you're benchmarking this program, you may want to suppress the output by
 * redirecting it to /dev/null. Also note that the Scanner class is horribly
 * inefficient, alas it's the simplest choice here.
 */
public final class UniqueQueue {
    private static Set<Integer> data;

    // Make checkstyle happy.
    private SetUnique() {}

    /**
     *  Main method.
     *  @param args Command line arguments (ignored).
     */
    public static void main(String[] args) {
//        data = new ListSet<Integer>();
        data = new BinaryHeapPriorityQueue<Integer>();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String s = scanner.next();
            try {
                int i = Integer.parseInt(s);
                data.insert(i);
            } catch (NumberFormatException e) {
                System.err.printf("Ignored non-integer %s\n", s);
            }
        }

        // maybe instead do while not empty and remove best


        for (Integer i: data) {
            System.out.println(i);
        }
    }
}
