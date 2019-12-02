package com.henu.oto.service;

import com.henu.oto.BaseTest;
import com.henu.oto.dto.ImageHolder;
import com.henu.oto.dto.ShopExecution;
import com.henu.oto.entity.Area;
import com.henu.oto.entity.PersonInfo;
import com.henu.oto.entity.Shop;
import com.henu.oto.entity.ShopCategory;
import com.henu.oto.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@Service
public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void tesGetShopList(){
        Shop shop = new Shop();
        ShopCategory shopCategory = new ShopCategory();

        shopCategory.setShopCategoryId(2L);
        shop.setShopCategory(shopCategory);
        ShopExecution shopExecution = shopService.getShopList(shop, 2, 2);

        System.out.println(shopExecution.getShopList().size());
        System.out.println(shopExecution.getCount());
    }

    @Test
    public void testModifyShop() throws SecurityException,FileNotFoundException{
        Shop shop = new Shop();
        shop.setShopId(7L);
        shop.setShopName("修改后的店铺名称");
        File shopImg = new File("E:\\校园商铺系统\\需要的图片\\images\\item\\shop\\26\\2017060609431259039.png");
        InputStream is = new FileInputStream(shopImg);

        ImageHolder imageHolder = new ImageHolder("2017060609431259039.png", is);
        ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
        System.out.println("新图片地址：" + shopExecution.getShop().getShopImg());
    }

    @Test
    public void testAddShop() throws FileNotFoundException {
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
        shop.setShopDesc("test7");
        shop.setShopAddr("test7");
        shop.setPhone("test7");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");

        File shopImg = new File("E:\\projectOto\\image\\2019082212434289705.png");
        InputStream is = new FileInputStream(shopImg);

        ImageHolder imageHolder = new ImageHolder(shopImg.getName(), is);
        ShopExecution shopExecution = shopService.addShop(shop, imageHolder);

        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }
}
