/*
 * Maria Coleman
 * mcolem31
 */

public class ChainingHashMapBench extends MapBenchBase {
    @Override
    Map<Integer, Integer> createUnit() {
        return new ChainingHashMap<>();
    }
}
