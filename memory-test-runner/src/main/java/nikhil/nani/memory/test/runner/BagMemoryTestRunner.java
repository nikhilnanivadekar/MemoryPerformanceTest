package nikhil.nani.memory.test.runner;

import java.util.HashMap;

import nikhil.nani.memory.measurer.MemoryTestUtils;
import org.eclipse.collections.impl.factory.Bags;
import org.openjdk.jol.vm.VM;

public class BagMemoryTestRunner
{
    public static void main(String[] args)
    {
        System.out.println(VM.current().details());
        BagMemoryTestRunner.memoryForECInteger();
        BagMemoryTestRunner.memoryForJDKInteger();
        BagMemoryTestRunner.memoryForJDKIntegerLongValue();
        BagMemoryTestRunner.memoryForECString();
        BagMemoryTestRunner.memoryForJDKString();
        BagMemoryTestRunner.memoryForJDKStringLongValue();
    }

    private static void memoryForJDKString()
    {
        MemoryTestUtils.memoryBenchStringBag(new HashMap<>());
    }

    private static void memoryForJDKStringLongValue()
    {
        MemoryTestUtils.memoryBenchStringBagLongValue(new HashMap<>());
    }

    private static void memoryForECString()
    {
        MemoryTestUtils.memoryBenchStringBag(Bags.mutable.empty());
    }

    private static void memoryForJDKInteger()
    {
        MemoryTestUtils.memoryBenchIntegerBag(new HashMap<>());
    }

    private static void memoryForJDKIntegerLongValue()
    {
        MemoryTestUtils.memoryBenchIntegerBagLongValue(new HashMap<>());
    }

    private static void memoryForECInteger()
    {
        MemoryTestUtils.memoryBenchIntegerBag(Bags.mutable.empty());
    }
}
