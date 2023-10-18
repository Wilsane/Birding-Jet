package com.example.aviaryquest.Data.Models;

public class ImageVariables {
    private String name,imageUrl,user;

    public ImageVariables(String name, String imageUrl,String user) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.user=user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
