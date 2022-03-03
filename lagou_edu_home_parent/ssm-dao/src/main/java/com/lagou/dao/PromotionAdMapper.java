package com.lagou.dao;

import com.lagou.domain.PromotionAd;

import java.util.List;

public interface PromotionAdMapper {

    /**
     * 分页查询广告信息
     * */
    public List<PromotionAd> findAllPromotionAdByPage();

    /**
     * 广告动态上下线
     * */
    public void updatePromotionAdStatus(PromotionAd promotionAd);

    /**
     * 新增广告
     * */
    void savePromotionAd(PromotionAd promotionAd);

    /**
     * 修改广告
     * */
    void updatePromotionAd(PromotionAd promotionAd);
}
