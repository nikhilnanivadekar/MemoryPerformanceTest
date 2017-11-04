package nikhil.nani.memory.test.runner;

import java.util.HashMap;

import nikhil.nani.memory.measurer.MemoryTestUtils;
import org.eclipse.collections.impl.factory.Maps;

public class MapMemoryTestRunner
{
    public static void main(String[] args)
    {
        MapMemoryTestRunner.memoryForECInteger();
        MapMemoryTestRunner.memoryForJDKInteger();
        MapMemoryTestRunner.memoryForECString();
        MapMemoryTestRunner.memoryForJDKString();
    }

    private static void memoryForJDKString()
    {
        MemoryTestUtils.memoryBenchString(new HashMap<>());
    }

    private static void memoryForECString()
    {
        MemoryTestUtils.memoryBenchString(Maps.mutable.empty());
    }

    private static void memoryForJDKInteger()
    {
        MemoryTestUtils.memoryBenchInteger(new HashMap<>());
    }

    private static void memoryForECInteger()
    {
        MemoryTestUtils.memoryBenchInteger(Maps.mutable.empty());
    }
}
