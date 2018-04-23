public class BinarySearchTreeMapBench extends OrderedMapBenchBase {
    @Override
    OrderedMap<Integer, Integer> createUnit() {
        return new BinarySearchTreeMap<>();
    }
}
