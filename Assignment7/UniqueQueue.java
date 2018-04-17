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
    private static PriorityQueue<Integer> data;

    // Make checkstyle happy.
    private UniqueQueue() {}

    /**
     *  Main method.
     *  @param args Command line arguments (ignored).
     */
    public static void main(String[] args) {
        //data = new SortedArrayPriorityQueue<Integer>();
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

        if (!data.empty()) {
            Integer temp = data.remove();
            System.out.println(temp);
            while (!data.empty()) {
                Integer temp2 = data.remove();
                // need to makesure its not in there before
                if (temp2.equals(temp)) {
                    continue;
                }
                System.out.println(temp2);
                temp = new Integer(temp2);
            }
        }
    }
}
