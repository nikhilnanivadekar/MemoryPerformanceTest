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
public class MapPutJmhTests
{
    @Param({"1000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000"})
    public int size;

    private Interval elements;

    @Setup
    public void setUp()
    {
        this.elements = Interval.fromTo(0, this.size);
    }

    @Benchmark
    public MutableMap<Integer, Integer> ecIntegerPut()
    {
        MutableMap<Integer, Integer> ec = Maps.mutable.empty();

        this.elements.each(each -> ec.put(each, -each));
        return ec;
    }

    @Benchmark
    public MutableMap<String, String> ecStringPut()
    {
        MutableMap<String, String> ec = Maps.mutable.empty();

        this.elements.each(each -> ec.put(String.valueOf(each), String.valueOf(-each)));
        return ec;
    }

    @Benchmark
    public Map<Integer, Integer> jdkIntegerPut()
    {
        Map<Integer, Integer> jdk = new HashMap<>();

        this.elements.each(each -> jdk.put(each, -each));
        return jdk;
    }

    @Benchmark
    public Map<String, String> jdkStringPut()
    {
        Map<String, String> jdk = new HashMap<>();

        this.elements.each(each -> jdk.put(String.valueOf(each), String.valueOf(-each)));
        return jdk;
    }
}
