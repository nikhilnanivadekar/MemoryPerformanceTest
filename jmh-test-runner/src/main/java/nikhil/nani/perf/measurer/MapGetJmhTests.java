package nikhil.nani.perf.measurer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;
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
public class MapGetJmhTests
{
    @Param({"1000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000"})
    public int size;

    private MutableMap<Integer, Integer> ecInteger;
    private MutableMap<String, String> ecString;
    private Map<Integer, Integer> jdkInteger;
    private Map<String, String> jdkString;

    private Interval elements;

    @Setup
    public void setUp()
    {
        this.elements = Interval.fromTo(0, this.size);
        this.ecInteger = Maps.mutable.empty();
        this.ecString = Maps.mutable.empty();
        this.jdkInteger = new HashMap<>();
        this.jdkString = new HashMap<>();
        this.elements.each(each ->
        {
            ecInteger.put(each, -each);
            ecString.put(String.valueOf(each), String.valueOf(-each));
            jdkInteger.put(each, -each);
            jdkString.put(String.valueOf(each), String.valueOf(-each));
        });
    }

    @Benchmark
    public int ecIntegerGet()
    {
        this.elements.each(each ->
        {
            if (this.ecInteger.get(each) != -each)
            {
                throw new IllegalStateException();
            }
        });
        return this.ecInteger.size();
    }

    @Benchmark
    public int ecStringGet()
    {
        this.elements.each(each ->
        {
            if (!this.ecString.get(String.valueOf(each)).equals(String.valueOf(-each)))
            {
                throw new IllegalStateException();
            }
        });
        return this.ecInteger.size();
    }

    @Benchmark
    public int jdkIntegerGet()
    {
        this.elements.each(each ->
        {
            if (this.jdkInteger.get(each) != -each)
            {
                throw new IllegalStateException();
            }
        });
        return this.ecInteger.size();
    }

    @Benchmark
    public int jdkStringGet()
    {
        this.elements.each(each ->
        {
            if (!this.jdkString.get(String.valueOf(each)).equals(String.valueOf(-each)))
            {
                throw new IllegalStateException();
            }
        });
        return this.ecInteger.size();
    }

}
