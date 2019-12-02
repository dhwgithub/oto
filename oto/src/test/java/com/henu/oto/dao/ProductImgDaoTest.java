package com.henu.oto.dao;

import com.henu.oto.BaseTest;
import com.henu.oto.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 按名字字典序执行测试方法，类前注解
 * 形成测试回环，不会影响数据库数据
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testCDeleteProductImgByProductId() throws Exception {
        long productId = 1;
        int effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testABatchInsertProductImg()throws Exception{
        ProductImg productImg = new ProductImg();
        productImg.setImgAddr("图片1");
        productImg.setImgDesc("测试图片1");
        productImg.setPriority(1);
        productImg.setCreateTime(new Date());
        productImg.setProductId(1L);

        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试图片2");
        productImg2.setPriority(2);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);

        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg);
        productImgList.add(productImg2);

        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
    }
}
