/*
 * Maria Coleman
 * mcolem31
 */

public class SimpleMapBench extends MapBenchBase {
    @Override
    Map<Integer, Integer> createUnit() {
        return new SimpleMap<>();
    }
}
