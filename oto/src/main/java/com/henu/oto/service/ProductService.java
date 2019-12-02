package com.henu.oto.service;

import com.henu.oto.dto.ImageHolder;
import com.henu.oto.dto.ProductExecution;
import com.henu.oto.entity.Product;
import com.henu.oto.exceptions.ProductOperationExeption;

import java.util.List;

public interface ProductService {
    /**
     * 查询商品列表并分页，可输入的条件有：商品名（模糊）、商品状态、店铺ID、商品类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     * 通过商品id查询唯一的商品信息
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * 修改商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImageHolderList
     * @return
     * @throws ProductOperationExeption
     */
    ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
        List<ImageHolder> productImageHolderList) throws ProductOperationExeption;

    /**
     * 添加商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationExeption
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail,
        List<ImageHolder> productImgList) throws ProductOperationExeption;
}
