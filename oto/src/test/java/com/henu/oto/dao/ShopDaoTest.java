package com.henu.oto.dao;

import com.henu.oto.BaseTest;
import com.henu.oto.entity.Area;
import com.henu.oto.entity.PersonInfo;
import com.henu.oto.entity.Shop;
import com.henu.oto.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopListAndCount(){
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();

        parentCategory.setShopCategoryId(1L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);

        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 44);
        assertEquals(5, shopList.size());

//        Shop shop = new Shop();
//        PersonInfo owner = new PersonInfo();
//
//
//        owner.setUserId(1L);
//        shop.setOwner(owner);
//        List<Shop> shopList = shopDao.queryShopList(shop, 0, 5);
//        System.out.println(shopList.size());
//        for (Shop shop1 : shopList){
//            System.out.println(shop1);
//        }
//
//        int count = shopDao.queryShopCount(shop);
//        System.out.println(count);
//
//
//        System.out.println("----------------------------------------");
//        ShopCategory shopCategory = new ShopCategory();
//        shopCategory.setShopCategoryId(2L);
//        shop.setShopCategory(shopCategory);
//        shopList = shopDao.queryShopList(shop, 0, 5);
//        count = shopDao.queryShopCount(shop);
//        System.out.println(shopList.size());
//        System.out.println(count);
    }

    @Test
    public void testQueryByShopId(){
        long shopId = 7;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop.getArea().getAreaId());
    }

    @Test
    public void testInsertShop(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺");
        shop.setShopDesc("test6");
        shop.setShopAddr("test6");
        shop.setPhone("test6");
//        shop.setShopImg("test5");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
//        shop.setAdvice("审核中");

        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();

        shop.setShopId(1L);
        shop.setShopDesc("更新");
        shop.setShopAddr("更新");
        shop.setLastEditTime(new Date());

        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }
}
