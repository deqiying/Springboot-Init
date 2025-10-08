package com.deqiying.web.business.rank;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RankLocalDB {
    // 每个rankKey对应一个SkipListMap (score -> members set)，但为了简单，用TreeMap模拟
    private static final Map<String, Map<String, Set<RankMember>>> rankMaps = new ConcurrentHashMap<>();

    public  boolean setRankMember(RankMember rankMember) {
        return false;
    }

    public  boolean removeRankMember(RankMember rankMember) {
        return false;
    }
}
