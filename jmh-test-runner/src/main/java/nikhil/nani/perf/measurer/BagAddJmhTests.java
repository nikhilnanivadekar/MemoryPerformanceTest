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
public class BagAddJmhTests
{
    @Param({"100", "1000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000"})
    public int size;

    private Interval elements;
    private MutableList<String> stringElements;

    @Setup
    public void setUp()
    {
        this.elements = Interval.fromTo(0, this.size);
        this.stringElements = Lists.mutable.empty();
        this.elements.each(each -> this.stringElements.add(String.valueOf(each)));
    }

    @Benchmark
    public MutableBag<Integer> ecIntegerAdd()
    {
        MutableBag<Integer> ec = Bags.mutable.empty();

        this.elements.each(ec::add);
        return ec;
    }

    @Benchmark
    public MutableBag<String> ecStringAdd()
    {
        MutableBag<String> ec = Bags.mutable.empty();

        this.stringElements.each(ec::add);
        return ec;
    }

    @Benchmark
    public Map<Integer, Integer> jdkIntegerAdd()
    {
        Map<Integer, Integer> jdk = new HashMap<>();

        this.elements.each(each -> jdk.put(each, 1));
        return jdk;
    }

    @Benchmark
    public Map<String, Integer> jdkStringAdd()
    {
        Map<String, Integer> jdk = new HashMap<>();

        this.stringElements.each(each -> jdk.put(each, 1));
        return jdk;
    }
}
