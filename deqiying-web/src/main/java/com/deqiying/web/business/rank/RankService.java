package com.deqiying.web.business.rank;

import java.util.List;

/**
 * 排行榜服务 (由 Kotlin 版本 RankService.kt 生成)
 * <p>注意: playerSimpleInfo 在 Kotlin 中有默认值 ""，在 Java 接口中通过默认方法重载实现。</p>
 */
public interface RankService {

    /**
     * 添加或更新排行榜成员分数
     * @param rankKey 排行榜标识
     * @param uid 成员唯一ID
     * @param score 分数
     * @param playerSimpleInfo 成员简单信息(可选，默认 "")
     * @return 是否成功
     */
    boolean setScore(String rankKey, String uid, long score, String playerSimpleInfo);

    /**
     * Kotlin 默认参数的 Java 等价：不传 playerSimpleInfo 时采用 ""。
     */
    default boolean setScore(String rankKey, String uid, long score) {
        return setScore(rankKey, uid, score, "");
    }

    /**
     * 删除排行榜成员
     * @param rankKey 排行榜标识
     * @param uid 成员唯一ID
     */
    void removeRankMember(String rankKey, String uid);

    /**
     * 删除整个排行榜
     * @param rankKey 排行榜标识
     */
    void removeAllByRankKey(String rankKey);

    /**
     * 查询排行榜（降序，start~end）
     * @param rankKey 排行榜标识
     * @param startRankNum 起始排名（包含，>=1）
     * @param endRankNum 结束排名（包含）
     * @return 成员列表
     */
    List<RankMember> getRankMembersByRange(String rankKey, long startRankNum, long endRankNum);

    /**
     * 查询排行榜, 优先基于缓存
     * @param rankKey 排行榜标识
     * @param startRankNum 起始排名（包含，>=1）
     * @param endRankNum 结束排名（包含）
     * @return 成员列表
     */
    List<RankMember> getRankMembersByRangeInCache(String rankKey, long startRankNum, long endRankNum);

    /**
     * 查询成员当前排名（从1开始）
     * @param rankKey 排行榜标识
     * @param uid 成员唯一ID
     * @return 排名（不存在可约定返回 -1 或 0，取决于具体实现）
     */
    long getMemberRank(String rankKey, String uid);

    /**
     * 查询成员分数
     * @param rankKey 排行榜标识
     * @param uid 成员唯一ID
     * @return 分数（不存在可约定返回 0，取决于具体实现）
     */
    long getMemberScore(String rankKey, String uid);
}

