package com.henu.oto.exceptions;

public class ProductOperationExeption extends RuntimeException {

    private static final long serialVersionUID = -874729902289392668L;

    public ProductOperationExeption(String msg){
        super(msg);
    }
}
