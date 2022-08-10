package com.gmail.l.domelink;

import com.gmail.l.domelink.SaveAnnotation;

public class Cars {

    @SaveAnnotation
    String brand;

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @SaveAnnotation
    String model;

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    @SaveAnnotation
    int horsePower;

    public int getHorsePower() {
        return horsePower;
    }
    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }
}
