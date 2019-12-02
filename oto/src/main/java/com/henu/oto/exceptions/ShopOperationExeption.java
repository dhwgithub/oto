package com.henu.oto.exceptions;

public class ShopOperationExeption extends RuntimeException {

    /**
     *  Java 类进行序列化也两个主要目的，分别为：
     *     把对象的字节序列永久地保存到硬盘上，通常存放在一个文件中；
     *     在网络上传送对象的字节序列。
     */
    private static final long serialVersionUID = -4355507497061483173L;


    public  ShopOperationExeption(String msg){
        super(msg);
    }
}
