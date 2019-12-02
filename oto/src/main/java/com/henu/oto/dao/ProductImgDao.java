package com.henu.oto.dao;

import com.henu.oto.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    /**
     * 批量添加商品详情照片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品下的所有详情图
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

    /**
     * 查询指定商品id的缩略图列表
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);
}
