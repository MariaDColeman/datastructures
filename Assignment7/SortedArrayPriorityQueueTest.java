import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Comparator;

/** Instantiate the class to test. */
public class SortedArrayPriorityQueueTest extends PriorityQueueTestBase {
    @Override
    PriorityQueue<Integer> createUnit() {
        return new SortedArrayPriorityQueue<>();
    }

    @Override
    PriorityQueue<Integer> createUnit(Comparator<Integer> cmp) {
        return new SortedArrayPriorityQueue<>(cmp);
    }

}
