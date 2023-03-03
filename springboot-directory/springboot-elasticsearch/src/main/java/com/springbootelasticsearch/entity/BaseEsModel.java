package com.springbootelasticsearch.entity;

/**
 * es模型基础信息，可通用
 *
 * @author Hu.ChangLiang
 * @date 2023/2/27 14:27
 */
public class BaseEsModel {
    /**
     * es文档id
     */
    private String id;

    /**
     * es索引
     */
    private String index;

    /**
     * es文档得分
     */
    private Float score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
