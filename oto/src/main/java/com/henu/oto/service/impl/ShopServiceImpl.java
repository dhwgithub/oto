package com.henu.oto.service.impl;

import com.henu.oto.dao.ShopDao;
import com.henu.oto.dto.ImageHolder;
import com.henu.oto.dto.ShopExecution;
import com.henu.oto.entity.Shop;
import com.henu.oto.enums.ShopStateEnum;
import com.henu.oto.exceptions.ShopOperationExeption;
import com.henu.oto.service.ShopService;
import com.henu.oto.util.ImageUtil;
import com.henu.oto.util.PageCalculator;
import com.henu.oto.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution shopExecution = new ShopExecution();
        if(shopList != null){
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
        }else {
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return shopExecution;
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationExeption {

        if (shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            try {
                //1. 判断是否需要处理图片
                if(thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())){
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null){
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, thumbnail);
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if(effectedNum <= 0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            }catch (Exception e){
                throw  new SecurityException("modifyShop error: " + e.getMessage());
            }
        }
    }

    @Override
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationExeption{
        // 空值判断
        if(shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        // 省略其他非空判断，如地区等

        try {
            // 给店铺信息初始化
            shop.setEnableStatus(0); //审核中
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            // 添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if(effectedNum <= 0){
                // 用RuntimeException可以使事务回滚
                throw new ShopOperationExeption(("店铺创建失败"));
            }else {
                if (thumbnail.getImage() != null){
                    // 存储图片
                    try {
                        addShopImg(shop, thumbnail);
                    }catch (Exception e){
                        throw new ShopOperationExeption("addShopImg error:" + e.getMessage());
                    }
                    // 更新店铺的图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if(effectedNum <= 0){
                        throw new ShopOperationExeption("更新图片地址失败");
                    }
                }
            }
        }catch (Exception e){
            throw new ShopOperationExeption("addShop error:" + e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        // 获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        shop.setShopImg(shopImgAddr);
    }
}
