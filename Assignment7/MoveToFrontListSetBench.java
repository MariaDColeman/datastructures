public class MoveToFrontListSetBench extends SetBenchBase {
    @Override
    Set<Integer> createUnit() {
        return new MoveToFrontListSet<>();
    }
}
