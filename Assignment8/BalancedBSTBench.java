public class BalancedBSTBench extends OrderedMapBenchBase {
    @Override
    OrderedMap<Integer, Integer> createUnit() {
        //return new AvlTreeMap<>();
        return new TreapMap<>();
    }
}
