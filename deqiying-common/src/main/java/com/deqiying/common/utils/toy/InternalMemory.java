package com.deqiying.common.utils.toy;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author deqiying
 * @date 2023-01-30
 */
final class InternalMemory {

    Map<String, String> getMemoryMap() {
        Runtime run = Runtime.getRuntime();

        long totalMemory = run.totalMemory();
        long freeMemory = run.freeMemory();
        long used = totalMemory - freeMemory;

        Map<String, String> map = new LinkedHashMap<>(3);
        map.put("used", format(used));
        map.put("freeMemory", format(freeMemory));
        map.put("totalMemory", format(totalMemory));

        return map;
    }

    private String format(long value) {

        var gb = 1024L * 1024L * 1024L;
        if (value >= gb) {
            return (Math.round((double) value / gb * 100.0D) / 100.0D) + "GB";
        }

        var mb = 1024L * 1024L;
        if (value >= mb) {
            return (Math.round((double) value / mb * 100.0D) / 100.0D) + "MB";
        }

        var kb = 1024L;
        if (value >= kb) {
            return (Math.round((double) value / kb * 100.0D) / 100.0D) + "KB";
        }

        return "";
    }
}
