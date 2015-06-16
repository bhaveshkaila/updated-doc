package com.musica.cortador.model;

/**
 * Created by Bhavesh.Kaila on 20-May-15.
 */
public class NavDrawerItem {
    private int imageId;
    private String title;

    public NavDrawerItem(){

    }
    public NavDrawerItem(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
