/*
 * Maria Coleman
 * mcolem31
 */

public class QuadProbingHashMapBench extends MapBenchBase {
    @Override
    Map<Integer, Integer> createUnit() {
        return new QuadProbingHashMap<>();
    }
}
