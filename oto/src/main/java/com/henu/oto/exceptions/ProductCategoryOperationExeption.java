package com.henu.oto.exceptions;

public class ProductCategoryOperationExeption extends RuntimeException {

    // 注意不能复制serialVersionUID，防止冲突异常
    private static final long serialVersionUID = -8291243028707573362L;

    public ProductCategoryOperationExeption(String msg){
        super(msg);
    }
}
