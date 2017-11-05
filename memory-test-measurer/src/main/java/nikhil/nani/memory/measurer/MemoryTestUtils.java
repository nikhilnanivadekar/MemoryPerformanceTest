package nikhil.nani.memory.measurer;

import java.util.Map;
import java.util.Set;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoryTestUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryTestUtils.class);

    public static void memoryBenchInteger(Map<Integer, Integer> map)
    {
        long constituentObjectSize = 0;
        MemoryTestUtils.printMemoryUtilization("Integer", map, constituentObjectSize, map.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            map.put(i, -i);
            constituentObjectSize += ObjectSizeCalculator.getObjectSize(i) + ObjectSizeCalculator.getObjectSize(-i);
            MemoryTestUtils.printMemoryUtilization("Integer",
                    map,
                    constituentObjectSize,
                    map.size());
        }
    }

    public static void memoryBenchInteger(Set<Integer> set)
    {
        long constituentObjectSize = 0;
        MemoryTestUtils.printMemoryUtilization("Integer", set, constituentObjectSize, set.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            set.add(i);
            constituentObjectSize += ObjectSizeCalculator.getObjectSize(i);
            MemoryTestUtils.printMemoryUtilization("Integer",
                    set,
                    constituentObjectSize,
                    set.size());
        }
    }

    public static void memoryBenchString(Map<String, String> map)
    {
        long constituentObjectSize = 0;
        MemoryTestUtils.printMemoryUtilization("String", map, constituentObjectSize, map.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            map.put(String.valueOf(i), String.valueOf(-i));
            constituentObjectSize += ObjectSizeCalculator.getObjectSize(String.valueOf(i)) + ObjectSizeCalculator.getObjectSize(String.valueOf(-i));
            MemoryTestUtils.printMemoryUtilization("String",
                    map,
                    constituentObjectSize,
                    map.size());
        }
    }

    public static void memoryBenchString(Set<String> set)
    {
        long constituentObjectSize = 0;
        MemoryTestUtils.printMemoryUtilization("String", set, constituentObjectSize, set.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            set.add(String.valueOf(i));
            constituentObjectSize += ObjectSizeCalculator.getObjectSize(String.valueOf(i));
            MemoryTestUtils.printMemoryUtilization("String",
                    set,
                    constituentObjectSize,
                    set.size());
        }
    }

    public static void printMemoryUtilization(String type, Object object, long constituentObjectSize, int size)
    {
        if (size % 10_000 == 0)
        {
            System.gc();
            System.gc();
            System.gc();
            LOGGER.info("{} Class {} Size:{} Memory:{} Kb",
                    type,
                    object.getClass(),
                    size,
                    MemoryTestUtils.measureKbs(object, constituentObjectSize));
        }
    }

    public static long measureKbs(Object object, long constituentObjectSize)
    {
        return (ObjectSizeCalculator.getObjectSize(object) - constituentObjectSize) / 1024;
    }
}
