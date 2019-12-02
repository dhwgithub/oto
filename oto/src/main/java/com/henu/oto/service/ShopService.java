package com.henu.oto.service;

import com.henu.oto.dto.ImageHolder;
import com.henu.oto.dto.ShopExecution;
import com.henu.oto.entity.Shop;
import com.henu.oto.exceptions.ShopOperationExeption;

public interface ShopService {

    /**
     * 根据shopCondition分页返回相应店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新商铺信息，包括图片处理
     * @param shop
     * @param thumbnail
     * @return
     * @throws ShopOperationExeption
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationExeption;

    /**
     * 注册店铺，包括图片处理
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationExeption;
}
