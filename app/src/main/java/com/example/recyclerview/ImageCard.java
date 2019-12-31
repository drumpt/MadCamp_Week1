package com.example.recyclerview;

import javax.security.auth.Destroyable;

public class ImageCard {
    private  String Title;
    private String Description;
    private int Thumbnail;

    public ImageCard(){

    }

    public ImageCard(String title,String description,int thumbnail){
        Title = title;
        Description = description;
        Thumbnail = thumbnail;
    }

    //Getter
    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    //Setter

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
