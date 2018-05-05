/*
 * Maria Coleman
 * mcolem31
 */

public class HashMapBench extends MapBenchBase {
    @Override
    Map<Integer, Integer> createUnit() {
        return new HashMap<>();
    }
}
