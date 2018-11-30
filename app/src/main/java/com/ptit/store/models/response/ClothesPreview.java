package com.ptit.store.models.response;

import java.io.Serializable;

/**
 * Created by KingIT on 4/22/2018.
 */

public class ClothesPreview implements Serializable{
    private String id;
    private String name;
    private int price;
    private String category;
    private String logoUrl;
    private int numberSave;
    private int numberAvageOfRate;
    private float avarageOfRate = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getNumberSave() {
        return numberSave;
    }

    public void setNumberSave(int numberSave) {
        this.numberSave = numberSave;
    }

    public int getNumberAvageOfRate() {
        return numberAvageOfRate;
    }

    public void setNumberAvageOfRate(int numberAvageOfRate) {
        this.numberAvageOfRate = numberAvageOfRate;
    }

    public float getAvarageOfRate() {
        return avarageOfRate;
    }

    public void setAvarageOfRate(float avarageOfRate) {
        this.avarageOfRate = avarageOfRate;
    }
}
