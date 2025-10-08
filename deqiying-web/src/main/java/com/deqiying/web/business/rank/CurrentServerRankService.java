package com.deqiying.web.business.rank;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurrentServerRankService implements RankService {

    @Resource
    private RankLocalDB db;

    // 服务器key，用于获取本服数据
    private String serverKey;

    @PostConstruct
    public void initRank() {
        serverKey = "current_server"; // 这里可以根据实际情况设置服务器标识
    }

    @Override
    public boolean setScore(String rankKey, String uid, long score, String playerSimpleInfo) {
        return db.setRankMember(RankMember.create(rankKey, uid, score, playerSimpleInfo));
    }

    @Override
    public void removeRankMember(String rankKey, String uid) {
        db.removeRankMember(RankMember.create(rankKey, uid, 0, ""));
    }

    @Override
    public void removeAllByRankKey(String rankKey) {

    }

    @Override
    public List<RankMember> getRankMembersByRange(String rankKey, long startRankNum, long endRankNum) {
        return List.of();
    }

    @Override
    public List<RankMember> getRankMembersByRangeInCache(String rankKey, long startRankNum, long endRankNum) {
        return List.of();
    }

    @Override
    public long getMemberRank(String rankKey, String uid) {
        return 0;
    }

    @Override
    public long getMemberScore(String rankKey, String uid) {
        return 0;
    }
}
