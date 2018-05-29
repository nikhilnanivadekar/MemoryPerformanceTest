package nikhil.nani.memory.measurer;

import java.util.Map;
import java.util.Set;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.eclipse.collections.api.bag.MutableBag;
import org.openjdk.jol.info.ClassData;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
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
            MemoryTestUtils.printMemoryUtilization("String",
                    map,
                    constituentObjectSize,
                    map.size());
        }
    }

    public static void memoryBenchStringBag(Map<String, Integer> map)
    {
        MemoryTestUtils.printMemoryUtilizationUsingJoi("String", map, map.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            map.put(String.valueOf(i), Integer.valueOf(1));
            MemoryTestUtils.printMemoryUtilizationUsingJoi("String",
                    map,
                    map.size());
        }
    }

    public static void memoryBenchStringBag(MutableBag<String> bag)
    {
        MemoryTestUtils.printMemoryUtilizationUsingJoi("String", bag, bag.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            bag.add(String.valueOf(i));
            MemoryTestUtils.printMemoryUtilizationUsingJoi("String",
                    bag,
                    bag.size());
        }
    }

    public static void memoryBenchIntegerBag(Map<Integer, Integer> map)
    {
        MemoryTestUtils.printMemoryUtilizationUsingJoi("Integer", map, map.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            map.put(i, Integer.valueOf(1));
            MemoryTestUtils.printMemoryUtilizationUsingJoi("Integer",
                    map,
                    map.size());
        }
    }

    public static void memoryBenchIntegerBag(MutableBag<Integer> bag)
    {
        MemoryTestUtils.printMemoryUtilizationUsingJoi("Integer", bag, bag.size());

        for (int i = 0; i < 1_000_000; i++)
        {
            bag.add(i);
            MemoryTestUtils.printMemoryUtilizationUsingJoi("Integer",
                    bag,
                    bag.size());
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

    public static void printMemoryUtilizationUsingJoi(String type, Object object, int size)
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
                    (GraphLayout.parseInstance(object).toPrintable()));
        }
    }

    public static long measureKbs(Object object, long constituentObjectSize)
    {
        return (ObjectSizeCalculator.getObjectSize(object) - constituentObjectSize) / 1024;
    }
}
