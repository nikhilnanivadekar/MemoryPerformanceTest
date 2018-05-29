package nikhil.nani.perf.measurer;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JmhRunner
{
    public static void main(String[] args) throws RunnerException
    {
        Options opt = new OptionsBuilder()
//                .include(MapGetJmhTests.class.getSimpleName())
//                .include(MapPutJmhTests.class.getSimpleName())
//                .include(SetAddJmhTests.class.getSimpleName())
//                .include(SetContainsJmhTests.class.getSimpleName())
//                .include(SetLoopJmhTests.class.getSimpleName())
                .include(BagAddJmhTests.class.getSimpleName())
                .include(BagOccurrencesJmhTests.class.getSimpleName())
                .forks(2)
                .warmupIterations(25)
                .measurementIterations(10)
                .mode(Mode.Throughput)
                .build();

        new Runner(opt).run();
    }
}
