public class ListSetBench extends SetBenchBase {
    @Override
    Set<Integer> createUnit() {
        return new ListSet<>();
    }
}
