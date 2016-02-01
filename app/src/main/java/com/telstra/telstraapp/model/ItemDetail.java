package com.telstra.telstraapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thahaseen on 2/1/2016.
 * Model class for the json Item Detail data. Each attribute is mapped to gson tags.
 */

public class ItemDetail {

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
