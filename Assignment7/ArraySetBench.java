public class ArraySetBench extends SetBenchBase {
    @Override
    Set<Integer> createUnit() {
        return new ArraySet<>();
    }
}
