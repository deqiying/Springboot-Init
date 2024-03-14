package com.deqiying.framework.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 统一的分页响应体
 *
 * @author deqiying
 * @version 1.0
 * @since 2024/1/7 22:46
 */
@Data
public class PageVO<T> {
    /**
     * 分页数据
     */
    private List<T> records;
    /**
     * 总条数
     */
    private Serializable total;
    /**
     * 总页数
     */
    private Serializable pages;
    /**
     * 当前页
     */
    private Serializable current;
    /**
     * 分页大小
     */
    private Serializable pageSize;
    /**
     * 服务响应时间戳
     */
    private Long timestamp;

    public PageVO(List<T> records, Serializable total, Serializable pages, Serializable current, Serializable pageSize) {
        this.records = records;
        this.total = total;
        this.pages = pages;
        this.current = current;
        this.pageSize = pageSize;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> PageVO<T> page(List<T> records, Serializable total, Serializable pages, Serializable current, Serializable pageSize) {
        return new PageVO<>(records, total, pages, current, pageSize);
    }
}