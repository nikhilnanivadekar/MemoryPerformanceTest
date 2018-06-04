package nikhil.nani.perf.measurer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.list.Interval;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
public class BagAddSameElementJmhTests
{
    @Param({"100", "1000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000"})
    public int size;

    private MutableBag<Integer> ecInteger;
    private MutableBag<String> ecString;
    private Map<Integer, Integer> jdkInteger;
    private Map<String, Integer> jdkString;

    private MutableList<String> strings;
    private MutableList<Integer> integers;

    @Setup
    public void setUp()
    {
        MutableList<Interval> intervals = Lists.mutable.withNValues(10, () -> Interval.oneTo(size));
        this.strings = intervals.asLazy().flatCollect(each -> each).collect(String::valueOf).toList();
        this.integers = intervals.asLazy().flatCollect(each -> each).toList();

        this.ecInteger = Bags.mutable.empty();
        this.ecString = Bags.mutable.empty();
        this.jdkInteger = new HashMap<>();
        this.jdkString = new HashMap<>();
    }

    @Benchmark
    public int ecIntegerAddSameElement()
    {
        this.integers.each(this.ecInteger::add);

        return this.ecInteger.size();
    }

    @Benchmark
    public int ecStringAddSameElement()
    {
        this.strings.each(this.ecString::add);

        return this.ecString.size();
    }

    @Benchmark
    public int jdkIntegerAddSameElement()
    {
        this.integers.each(each ->
        {
            Integer occurrences = this.jdkInteger.computeIfAbsent(each, count -> 0);
            this.jdkInteger.put(each, ++occurrences);
        });
        return this.jdkInteger.size();
    }

    @Benchmark
    public int jdkStringAddSameElement()
    {
        this.strings.each(each ->
        {
            Integer occurrences = this.jdkString.computeIfAbsent(each, count -> 0);
            this.jdkString.put(each, ++occurrences);
        });
        return this.jdkString.size();
    }
}
