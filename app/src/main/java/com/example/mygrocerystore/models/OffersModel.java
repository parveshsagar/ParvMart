package com.example.mygrocerystore.models;

public class OffersModel {

    String name;
    String price;
    String img_url;

    public OffersModel() {
    }

    public OffersModel(String name, String price, String img_url) {
        this.name = name;
        this.price = price;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
