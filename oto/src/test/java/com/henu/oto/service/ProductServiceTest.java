package com.henu.oto.service;

import com.henu.oto.BaseTest;
import com.henu.oto.dto.ImageHolder;
import com.henu.oto.dto.ProductExecution;
import com.henu.oto.entity.Product;
import com.henu.oto.entity.ProductCategory;
import com.henu.oto.entity.Shop;
import com.henu.oto.enums.ProductStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testpdifyProduct() throws Exception{
        Product product = new Product();
        Shop shop = new Shop();
        ProductCategory productCategory = new ProductCategory();

        shop.setShopId(7L);
        productCategory.setProductCategoryId(1L);

        product.setProductId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("正式的商品");
        product.setProductDesc("正式的商品");

        // 创建缩略图文件流
        File thumbnailFile = new File("E:\\projectOto\\test\\2017060609431259039.png");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);

        // 创建两个商品详情图文件流并将它们添加到详情图表中
        File productImg1 = new File("E:\\projectOto\\test\\2019082212434289705.png");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("E:\\projectOto\\test\\2017061320393452772.jpg");
        InputStream is2 = new FileInputStream(productImg2);

        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(), is1));
        productImgList.add(new ImageHolder(productImg2.getName(), is2));

        // 添加商品并验证
        ProductExecution productExecution = productService.modifyProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());
    }

    @Test
    public void testAddProduct() throws Exception{
        // 创建shopId为1且productCategoryId为1的商品实例并给其成员赋值
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(7L);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);

        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

        // 创建缩略图文件流
        File thumbnailFile = new File("E:\\projectOto\\test\\2017060609084595067.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);

        // 创建两个商品详情文件流并将它们添加到详情图列表中
        File productImg1 = new File("E:\\projectOto\\test\\2017060609084595067.jpg");
        InputStream is1 = new FileInputStream(thumbnailFile);
        File productImg2 = new File("E:\\projectOto\\test\\2017060609431259039.png");
        InputStream is2 = new FileInputStream(thumbnailFile);

        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(), is1));
        productImgList.add(new ImageHolder(productImg2.getName(), is2));

        // 添加商品并验证
        ProductExecution productExecution = productService.addProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), productExecution.getState());
    }
}
