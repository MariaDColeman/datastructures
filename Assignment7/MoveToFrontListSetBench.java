import java.util.Random;

public class MoveToFrontListSetBench extends SetBenchBase {
    private static final int SIZE = 1024;
    private static final Random r = new Random();
    @Override
    Set<Integer> createUnit() {
        return new MoveToFrontListSet<>();
    }

//    @Override
//    private static void removeRandom(Set<Integer> s) {
//        for (int i = 0; i < SIZE; i++) {
//            s.remove(r.nextInt(SIZE*SIZE));
//        }
//    }

}
