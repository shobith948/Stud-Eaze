package com.example.studeaze;

public class table {
    private String semester;
    private String mImageUrl;
    public table() { //empty constructor needed
    }
    //parameterised constructor
    public table(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        semester = name;
        mImageUrl = imageUrl;
    }
    //getter and setter
    public String getName() {
        return semester;
    }
    public void setName(String name) {
        semester = name;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}