package com.deqiying.web.business.rank;

import lombok.Data;

/**
 * 排行榜成员数据传输对象 (由 Kotlin 版本 RankMemberDto.kt 转换)
 * 原 Kotlin 字段均为可变 var，这里提供标准的 Java POJO 形式。
 * 未保留 @Column 注解（当前模块未引入 JPA 依赖，如需要可按需添加 jakarta.persistence.Column）。
 */
@Data
public class RankMember {

    /**
     * 排行榜标识
     */
    private String rankKey = "";
    /**
     * 唯一id = 玩家id / 联盟id / 服务器id
     */
    private String uid = "";
    /**
     * 积分
     */
    private long score = 0L;

    /**
     * 简要信息: 玩家信息 / 联盟信息
     */
    private String info = "";

    public static RankMember create(String rankKey, String uid, long score, String info) {
        RankMember rankMember = new RankMember();
        rankMember.setRankKey(rankKey);
        rankMember.setUid(uid);
        rankMember.setScore(score);
        rankMember.setInfo(info);
        return rankMember;
    }
}

