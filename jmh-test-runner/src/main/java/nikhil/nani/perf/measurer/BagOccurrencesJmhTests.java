package nikhil.nani.perf.measurer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.impl.factory.Bags;
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
public class BagOccurrencesJmhTests
{
    @Param({"1000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000"})
    public int size;

    private MutableBag<Integer> ecInteger;
    private MutableBag<String> ecString;
    private Map<Integer, Integer> jdkInteger;
    private Map<String, Integer> jdkString;

    private Interval elements;

    @Setup
    public void setUp()
    {
        this.elements = Interval.fromTo(0, this.size);
        this.ecInteger = Bags.mutable.empty();
        this.ecString = Bags.mutable.empty();
        this.jdkInteger = new HashMap<>();
        this.jdkString = new HashMap<>();
        this.elements.each(each ->
        {
            ecInteger.add(each);
            ecString.add(String.valueOf(each));
            jdkInteger.put(each, 1);
            jdkString.put(String.valueOf(each), 1);
        });
    }

    @Benchmark
    public int ecIntegerOccurrencesOf()
    {
        this.elements.each(each ->
        {
            if (this.ecInteger.occurrencesOf(each) != 1)
            {
                throw new IllegalStateException();
            }
        });
        return this.ecInteger.size();
    }

    @Benchmark
    public int ecStringOccurrencesOf()
    {
        this.elements.each(each ->
        {
            if (this.ecString.occurrencesOf(String.valueOf(each)) != 1)
            {
                throw new IllegalStateException();
            }
        });
        return this.ecString.size();
    }

    @Benchmark
    public int jdkIntegerOccurrencesOf()
    {
        this.elements.each(each ->
        {
            if (this.jdkInteger.get(each) != 1)
            {
                throw new IllegalStateException();
            }
        });
        return this.jdkInteger.size();
    }

    @Benchmark
    public int jdkStringOccurrencesOf()
    {
        this.elements.each(each ->
        {
            if (this.jdkString.get(String.valueOf(each)) != 1)
            {
                throw new IllegalStateException();
            }
        });
        return this.jdkString.size();
    }
}
