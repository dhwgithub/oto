package com.henu.oto.entity;

import java.util.Date;

public class ProductImg {
    private Long productImgId;
    private String imgAddr;
    private String imgDesc;
    private Integer priority;
    private Date createTime;
    private Long productId;

    @Override
    public String toString() {
        return "ProductImg{" +
                "productImgId=" + productImgId +
                ", imgAddr='" + imgAddr + '\'' +
                ", imgDesc='" + imgDesc + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", productId=" + productId +
                '}';
    }

    public void setProductImgId(Long productImgId) {
        this.productImgId = productImgId;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductImgId() {
        return productImgId;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public Integer getPriority() {
        return priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Long getProductId() {
        return productId;
    }
}
