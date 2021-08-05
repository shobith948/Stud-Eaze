package com.example.studeaze;

public class Model { //Image model class
    private String imageUrl;
    public Model(){

    }
    public Model(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
