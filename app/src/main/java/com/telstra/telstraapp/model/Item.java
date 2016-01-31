package com.telstra.telstraapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thahaseen on 1/31/2016.
 * Model class for the json data. Each attribute is mapped to gson tags.
 */

public class Item {
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("imageHref")
    String imageHref;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }
}
