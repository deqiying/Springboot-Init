package com.deqiying.common.utils.toy;


import org.fusesource.jansi.Ansi;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author deqiying
 * @date 2023-01-30
 */
final class ToyTable {
    Ansi.Color color = BannerColorStrategy.anyColor();
    Map<String, ToyTableRegion> regionMap = new LinkedHashMap<>();
    ToyTableRender tableRender;
    int bodyMaxNum;

    void render() {

        this.bodyMaxNum = this.countRegionMaxLine();
        this.tableRender = new ToyTableRender(bodyMaxNum);

        int size = this.regionMap.size();
        int eleNum = 0;

        for (ToyTableRegion region : regionMap.values()) {
            region.render();
            eleNum++;

            boolean lastEle = size == eleNum;
            if (!lastEle) {
                tableRender.next();
            }
        }

        this.tableRender.render();
    }

    int countRegionMaxLine() {
        int lineMax = 0;

        for (ToyTableRegion region : this.regionMap.values()) {

            int size = region.lineMap.size();
            if (size > lineMax) {
                lineMax = size;
            }
        }

        return lineMax;
    }

    ToyTableRegion getRegion(String regionName) {

        ToyTableRegion region = this.regionMap.get(regionName);

        if (Objects.isNull(region)) {

            region = new ToyTableRegion();
            region.head = regionName;
            region.table = this;

            this.regionMap.putIfAbsent(regionName, region);
            region = this.regionMap.get(regionName);
        }

        return region;
    }
}
