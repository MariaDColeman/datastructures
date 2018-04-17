import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

import java.util.Random;

/**
 * Comparing various Set implementations. There are intentionally no deeper
 * explanations for the benchmark methods, you're supposed to think about them
 * yourself. But keep in mind that we probably didn't give you anything that's
 * completely useless...
 */
public abstract class SetBenchBase {
    private static final int SIZE = 1024;
    private static final Random r = new Random();
    private static boolean t;

    abstract Set<Integer> createUnit();

    // Code to benchmark.

    private static void insertLinear(Set<Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            s.insert(i);
        }
    }

    private static void insertRandom(Set<Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            s.insert(r.nextInt(SIZE*SIZE));
        }
    }

    private static void removeLinear(Set<Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            s.remove(i);
        }
    }

    private static void removeRandom(Set<Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            s.remove(r.nextInt(SIZE*SIZE));
        }
    }

    private static void hasLinear(Set<Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            t = s.has(i);
        }
    }

    private static void hasRandom(Set<Integer> s) {
        for (int i = 0; i < SIZE; i++) {
            t = s.has(r.nextInt(SIZE*SIZE));
        }
    }

//    private static void hasRandom(Set<Integer> s) {
//        for (int i = 0; i < SIZE/2; i++) {
//            t = s.has(r.nextInt(SIZE/2*SIZE/2));
//        }
//    }


    private static void hasBiased(Set<Integer> s) {
        for (int i = 0; i < SIZE/8; i++) {
            t = s.has(0);
            t = s.has(0);
            t = s.has(i);
            t = s.has(i);
            t = s.has(SIZE-i-1);
            t = s.has(SIZE-i-1);
            t = s.has(SIZE-1);
            t = s.has(SIZE-1);
        }
    }

    // Individual benchmarks.

    @Bench
    public void insertLinear(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Set<Integer> s = this.createUnit();
            b.start();

            insertLinear(s);
        }
    }

    @Bench
    public void insertRandom(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Set<Integer> s = this.createUnit();
            b.start();

            insertRandom(s);
        }
    }

    @Bench
    public void removeLinear(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Set<Integer> s = this.createUnit();
            insertLinear(s);
            b.start();

            removeLinear(s);
        }
    }

    @Bench
    public void removeRandom(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Set<Integer> s = this.createUnit();
            insertLinear(s);
            b.start();

            removeRandom(s);
        }
    }

    @Bench
    public void hasLinear(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Set<Integer> s = this.createUnit();
            insertLinear(s);
            b.start();

            hasLinear(s);
        }
    }

    @Bench
    public void hasRandom(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Set<Integer> s = this.createUnit();
            insertLinear(s);
            b.start();

            hasRandom(s);
        }
    }

    @Bench
    public void hasBiased(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            Set<Integer> s = this.createUnit();
            insertLinear(s);
            b.start();

            hasBiased(s);
        }
    }
}
