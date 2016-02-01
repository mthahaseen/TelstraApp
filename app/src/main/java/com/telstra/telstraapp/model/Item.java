package com.telstra.telstraapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thahaseen on 1/31/2016.
 * Model class for the json Item data. Each attribute is mapped to gson tags.
 */

public class Item {

    @SerializedName("title")
    String pTitle;

    @SerializedName("rows")
    List<ItemDetail> lstData = new ArrayList<ItemDetail>();

    public List<ItemDetail> getLstData() {
        return lstData;
    }

    public void setLstData(List<ItemDetail> lstData) {
        this.lstData = lstData;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }
}
