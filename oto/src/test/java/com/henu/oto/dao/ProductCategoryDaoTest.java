package com.henu.oto.dao;

import com.henu.oto.BaseTest;
import com.henu.oto.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testQueryByShopId() throws Exception{
        long shopId = 69;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println(productCategoryList.size());
    }

    @Test
    public void testBatchInsertProductCategory() throws Exception{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商品类别1");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(69L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别1");
        productCategory2.setPriority(1);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(69L);

        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);

        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testDeleteProductCategory() throws Exception{
        long shopId = 69;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        for (ProductCategory productCategory : productCategoryList){
            if ("商品类别1".equals(productCategory.getProductCategoryName())){
                int effectedNum = productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(), shopId);
                assertEquals(1, effectedNum);
            }
        }
    }

}
