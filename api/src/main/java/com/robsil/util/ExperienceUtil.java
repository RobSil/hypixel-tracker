package com.robsil.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Data
@Service
@NoArgsConstructor
public class ExperienceUtil {

    private Map<Integer, Integer> universalLevelToCumulativeXp = new HashMap<>(Map.ofEntries(
       new SimpleEntry<Integer, Integer>(0, 0),
       new SimpleEntry<Integer, Integer>(1, 50),
       new SimpleEntry<Integer, Integer>(2, 175),
       new SimpleEntry<Integer, Integer>(3, 375),
       new SimpleEntry<Integer, Integer>(4, 675),
       new SimpleEntry<Integer, Integer>(5, 1175),
       new SimpleEntry<Integer, Integer>(6, 1925),
       new SimpleEntry<Integer, Integer>(7, 2925),
       new SimpleEntry<Integer, Integer>(8, 4425),
       new SimpleEntry<Integer, Integer>(9, 6425),
       new SimpleEntry<Integer, Integer>(10, 9925),
       new SimpleEntry<Integer, Integer>(11, 14925),
       new SimpleEntry<Integer, Integer>(12, 22425),
       new SimpleEntry<Integer, Integer>(13, 32425),
       new SimpleEntry<Integer, Integer>(14, 47425),
       new SimpleEntry<Integer, Integer>(15, 67425),
       new SimpleEntry<Integer, Integer>(16, 97425),
       new SimpleEntry<Integer, Integer>(17, 147425),
       new SimpleEntry<Integer, Integer>(18, 222425),
       new SimpleEntry<Integer, Integer>(19, 322425),
       new SimpleEntry<Integer, Integer>(20, 522425),
       new SimpleEntry<Integer, Integer>(21, 822425),
       new SimpleEntry<Integer, Integer>(22, 1222425),
       new SimpleEntry<Integer, Integer>(23, 1722425),
       new SimpleEntry<Integer, Integer>(24, 2322425),
       new SimpleEntry<Integer, Integer>(25, 3022425),
       new SimpleEntry<Integer, Integer>(26, 3822425),
       new SimpleEntry<Integer, Integer>(27, 4722425),
       new SimpleEntry<Integer, Integer>(28, 5722425),
       new SimpleEntry<Integer, Integer>(29, 6822425),
       new SimpleEntry<Integer, Integer>(30, 8022425),
       new SimpleEntry<Integer, Integer>(31, 9322425),
       new SimpleEntry<Integer, Integer>(32, 10722425),
       new SimpleEntry<Integer, Integer>(33, 12222425),
       new SimpleEntry<Integer, Integer>(34, 13822425),
       new SimpleEntry<Integer, Integer>(35, 15522425),
       new SimpleEntry<Integer, Integer>(36, 17322425),
       new SimpleEntry<Integer, Integer>(37, 19222425),
       new SimpleEntry<Integer, Integer>(38, 21222425),
       new SimpleEntry<Integer, Integer>(39, 23322425),
       new SimpleEntry<Integer, Integer>(40, 25522425),
       new SimpleEntry<Integer, Integer>(41, 27822425),
       new SimpleEntry<Integer, Integer>(42, 30222425),
       new SimpleEntry<Integer, Integer>(43, 32722425),
       new SimpleEntry<Integer, Integer>(44, 35322425),
       new SimpleEntry<Integer, Integer>(45, 38072425),
       new SimpleEntry<Integer, Integer>(46, 40972425),
       new SimpleEntry<Integer, Integer>(47, 44072425),
       new SimpleEntry<Integer, Integer>(48, 47472425),
       new SimpleEntry<Integer, Integer>(49, 51172425),
       new SimpleEntry<Integer, Integer>(50, 55172425),
       new SimpleEntry<Integer, Integer>(51, 59472425),
       new SimpleEntry<Integer, Integer>(52, 64072425),
       new SimpleEntry<Integer, Integer>(53, 68972425),
       new SimpleEntry<Integer, Integer>(54, 74172425),
       new SimpleEntry<Integer, Integer>(55, 79672425),
       new SimpleEntry<Integer, Integer>(56, 85472425),
       new SimpleEntry<Integer, Integer>(57, 91572425),
       new SimpleEntry<Integer, Integer>(58, 97972425),
       new SimpleEntry<Integer, Integer>(59, 104672425),
       new SimpleEntry<Integer, Integer>(60, 111672425)
    ));

    private Map<Integer, Integer> dungeoneeringLevelToCumulativeXp = new HashMap<>(Map.ofEntries(
            new SimpleEntry<>(0, 0),
            new SimpleEntry<>(1, 50),
            new SimpleEntry<>(2, 125),
            new SimpleEntry<>(3, 235),
            new SimpleEntry<>(4, 395),
            new SimpleEntry<>(5, 625),
            new SimpleEntry<>(6, 955),
            new SimpleEntry<>(7, 1425),
            new SimpleEntry<>(8, 2095),
            new SimpleEntry<>(9, 3045),
            new SimpleEntry<>(10, 4385),
            new SimpleEntry<>(11, 6275),
            new SimpleEntry<>(12, 8940),
            new SimpleEntry<>(13, 12700),
            new SimpleEntry<>(14, 17960),
            new SimpleEntry<>(15, 25340),
            new SimpleEntry<>(16, 35640),
            new SimpleEntry<>(17, 50040),
            new SimpleEntry<>(18, 70040),
            new SimpleEntry<>(19, 97640),
            new SimpleEntry<>(20, 135640),
            new SimpleEntry<>(21, 188140),
            new SimpleEntry<>(22, 259640),
            new SimpleEntry<>(23, 356640),
            new SimpleEntry<>(25, 488640),
            new SimpleEntry<>(26, 911640),
            new SimpleEntry<>(27, 1239640),
            new SimpleEntry<>(28, 1684640),
            new SimpleEntry<>(29, 2284640),
            new SimpleEntry<>(30, 3084640),
            new SimpleEntry<>(31, 4149640),
            new SimpleEntry<>(32, 5559640),
            new SimpleEntry<>(33, 7459640),
            new SimpleEntry<>(34, 9959640),
            new SimpleEntry<>(35, 13259640),
            new SimpleEntry<>(36, 17559640),
            new SimpleEntry<>(37, 23159640),
            new SimpleEntry<>(38, 30359640),
            new SimpleEntry<>(39, 39559640),
            new SimpleEntry<>(40, 51559640),
            new SimpleEntry<>(41, 66559640),
            new SimpleEntry<>(42, 85559640),
            new SimpleEntry<>(43, 109559640),
            new SimpleEntry<>(44, 139559640),
            new SimpleEntry<>(45, 177559640),
            new SimpleEntry<>(46, 225559640),
            new SimpleEntry<>(48, 360559640),
            new SimpleEntry<>(49, 453559640),
            new SimpleEntry<>(50, 569803640)
    ));

    private Map<Integer, Integer> runecrafingLevelToCumulativeXp = new HashMap<>(Map.ofEntries(

    ));

    private Map<Integer, Integer> socialLevelToCumulativeXp = new HashMap<>(Map.ofEntries(

    ));


    public int universalXpToLevel(int xp) {
        return universalLevelToCumulativeXp.entrySet()
                .stream()
                .filter(entry -> entry.getValue() - 1 < xp)
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .map(Entry::getKey)
                .findFirst().orElse(0);
    }

    public int dungeoneeringXpToLevel(int xp) {
        return dungeoneeringLevelToCumulativeXp.entrySet()
                .stream()
                .filter(entry -> entry.getValue() - 1 < xp)
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .map(Entry::getKey)
                .findFirst().orElse(0);
    }

}
