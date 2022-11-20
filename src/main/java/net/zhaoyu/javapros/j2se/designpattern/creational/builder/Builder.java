package net.zhaoyu.javapros.j2se.designpattern.creational.builder;

public class Builder {
    protected Product product = new Product();

    public void name(String name){
        product.setName(name);
    }

    public void tel(String tel){
        product.setTel(tel);
    }

    public void address(String address){
        product.setAddress(address);
    }

    public Product build(){
        return product;
    }
}
