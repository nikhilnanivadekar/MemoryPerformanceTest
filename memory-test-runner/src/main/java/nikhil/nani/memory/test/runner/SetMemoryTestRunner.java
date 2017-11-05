package nikhil.nani.memory.test.runner;

import java.util.HashSet;

import nikhil.nani.memory.measurer.MemoryTestUtils;
import org.eclipse.collections.impl.factory.Sets;

public class SetMemoryTestRunner
{
    public static void main(String[] args)
    {
        SetMemoryTestRunner.memoryForECInteger();
        SetMemoryTestRunner.memoryForJDKInteger();
        SetMemoryTestRunner.memoryForECString();
        SetMemoryTestRunner.memoryForJDKString();
    }

    private static void memoryForJDKString()
    {
        MemoryTestUtils.memoryBenchString(new HashSet<>());
    }

    private static void memoryForECString()
    {
        MemoryTestUtils.memoryBenchString(Sets.mutable.empty());
    }

    private static void memoryForJDKInteger()
    {
        MemoryTestUtils.memoryBenchInteger(new HashSet<>());
    }

    private static void memoryForECInteger()
    {
        MemoryTestUtils.memoryBenchInteger(Sets.mutable.empty());
    }
}
