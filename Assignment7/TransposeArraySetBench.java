public class TransposeArraySetBench extends SetBenchBase {
    @Override
    Set<Integer> createUnit() {
        return new TransposeArraySet<>();
    }
}
