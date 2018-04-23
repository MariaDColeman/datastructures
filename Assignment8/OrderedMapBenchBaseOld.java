import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

import java.util.Random;
import java.util.ArrayList;

/**
 * Comparing various OrderedMap implementations.
 */
public abstract class OrderedMapBenchBase {
    private static final int SIZE = 1024;
    private static final int INIT = 1;
//    private static final Random r = new Random();
    private static final Random r = new Random(123456789L);
    private static boolean t;

    

    private static void init() {
        private ArrayList<Integer> tempList = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            tempList.add(i);
        }
        private Integer[] ra = new Integer[SIZE];
        //ra[0]=-1;
        for (int c = 0; c < SIZE; c++) {
            ra[c] = tempList.remove((int)(Math.random() * tempList.size()));
        }

        private ArrayList<Integer> tempList2 = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            tempList2.add(i);
        }
        private Integer[] ra2 = new Integer[SIZE];
        //ra[0]=-1;
        for (int c = 0; c < SIZE; c++) {
            ra2[c] = tempList2.remove((int)(Math.random() * tempList2.size()));
        }

    }



    abstract OrderedMap<Integer, Integer> createUnit();

    // Code to benchmark.

    private static void insertLinear(OrderedMap<Integer, Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            s.insert(i, INIT);
        }
    }

    private static void insertRandom(OrderedMap<Integer, Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            s.insert(ra[i], INIT);
        }
    }

    private static void removeLinear(OrderedMap<Integer, Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            s.remove(i);
        }
    }

    private static void removeRandom(OrderedMap<Integer, Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            s.remove(ra2[i]);
        }
    }

    private static void hasLinear(OrderedMap<Integer, Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            t = s.has(i);
        }
    }

    private static void hasRandom(OrderedMap<Integer, Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            t = s.has(ra[i]);
        }
    }

//    private static void hasRandom(Set<Integer> s) {
//        for (int i = 0; i < SIZE/2; i++) {
//            t = s.has(r.nextInt(SIZE/2*SIZE/2));
//        }
//    }


//    private static void hasBiased(OrderedMap<Integer, Integer> s) {
//        for (int i = 0; i < SIZE/8; i++) {
//            t = s.has(0);
//            t = s.has(0);
//            t = s.has(i);
//            t = s.has(i);
//            t = s.has(SIZE-i-1);
//            t = s.has(SIZE-i-1);
//            t = s.has(SIZE-1);
//            t = s.has(SIZE-1);
//        }
//    }

    // Individual benchmarks.

    @Bench
    public void insertLinear(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            OrderedMap<Integer, Integer> s = this.createUnit();
            b.start();

            insertLinear(s);
        }
    }

    @Bench
    public void insertRandom(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            OrderedMap<Integer, Integer> s = this.createUnit();
            b.start();

            insertRandom(s);
        }
    }

    @Bench
    public void removeLinear(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            OrderedMap<Integer, Integer> s = this.createUnit();
            insertLinear(s);
            b.start();

            removeLinear(s);
        }
    }

    @Bench
    public void removeRandom(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            OrderedMap<Integer, Integer> s = this.createUnit();
            insertLinear(s);
            b.start();

            removeRandom(s);
        }
    }

    @Bench
    public void hasLinear(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            OrderedMap<Integer, Integer> s = this.createUnit();
            insertLinear(s);
            b.start();

            hasLinear(s);
        }
    }

    @Bench
    public void hasRandom(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            OrderedMap<Integer, Integer> s = this.createUnit();
            insertLinear(s);
            b.start();

            hasRandom(s);
        }
    }

//    @Bench
//    public void hasBiased(Bee b) {
//        for (int n = 0; n < b.reps(); n++) {
//            b.stop();
//            OrderedMap<Integer, Integer> s = this.createUnit();
//            insertLinear(s);
//            b.start();

//            hasBiased(s);
//        }
//    }
}
