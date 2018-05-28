package nikhil.nani.perf.measurer;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Sets;
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
public class SetLoopJmhTests
{
    @Param({"100", "1000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000"})
    public int size;

    private MutableSet<Integer> ecInteger;
    private MutableSet<String> ecString;
    private Set<Integer> jdkInteger;
    private Set<String> jdkString;

    private Interval elements;

    @Setup
    public void setUp()
    {
        this.elements = Interval.fromTo(0, this.size);
        this.ecInteger = Sets.mutable.empty();
        this.ecString = Sets.mutable.empty();
        this.jdkInteger = new HashSet<>();
        this.jdkString = new HashSet<>();
        this.elements.each(each ->
        {
            ecInteger.add(each);
            ecString.add(String.valueOf(each));
            jdkInteger.add(each);
            jdkString.add(String.valueOf(each));
        });
    }

    @Benchmark
    public int ecIntegerLoop()
    {
        this.ecInteger.each(each ->
        {
            if (each == null)
            {
                throw new IllegalStateException();
            }
        });
        return this.ecInteger.size();
    }

    @Benchmark
    public int ecStringLoop()
    {
        this.ecString.each(each ->
        {
            if (each == null)
            {
                throw new IllegalStateException();
            }
        });
        return this.ecString.size();
    }

    @Benchmark
    public int jdkIntegerLoop()
    {
        this.jdkInteger.forEach(each ->
        {
            if (each == null)
            {
                throw new IllegalStateException();
            }
        });
        return this.jdkInteger.size();
    }

    @Benchmark
    public int jdkStringLoop()
    {
        this.jdkString.forEach(each ->
        {
            if (each == null)
            {
                throw new IllegalStateException();
            }
        });
        return this.jdkString.size();
    }
}
