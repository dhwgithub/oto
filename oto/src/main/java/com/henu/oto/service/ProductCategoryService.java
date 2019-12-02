package com.henu.oto.service;

import com.henu.oto.dto.ProductCategoryExecution;
import com.henu.oto.entity.ProductCategory;
import com.henu.oto.exceptions.ProductCategoryOperationExeption;

import java.util.List;

public interface ProductCategoryService {



    /**
     * 查询指定某个店铺下的所有商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    /**
     * 批量添加分类
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationExeption
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationExeption;

    /**
     * 将此类别下的商品里的类别id置为空，再删除该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationExeption
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryOperationExeption;
}
