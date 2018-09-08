package com.crmmarketing.hmt.poductmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrList {

    @SerializedName("prd_id")
    @Expose
    private String prdId;
    @SerializedName("prd_categoryid")
    @Expose
    private String prdCategoryid;
    @SerializedName("prd_name")
    @Expose
    private String prdName;
    @SerializedName("prd_desc")
    @Expose
    private String prdDesc;
    @SerializedName("prd_price")
    @Expose
    private String prdPrice;
    @SerializedName("pcat_name")
    @Expose
    private String pcatName;

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    private boolean isAdded;

    public String getPrdId() {
        return prdId;
    }

    public void setPrdId(String prdId) {
        this.prdId = prdId;
    }

    public String getPrdCategoryid() {
        return prdCategoryid;
    }

    public void setPrdCategoryid(String prdCategoryid) {
        this.prdCategoryid = prdCategoryid;
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName;
    }

    public String getPrdDesc() {
        return prdDesc;
    }

    public void setPrdDesc(String prdDesc) {
        this.prdDesc = prdDesc;
    }

    public String getPrdPrice() {
        return prdPrice;
    }

    public void setPrdPrice(String prdPrice) {
        this.prdPrice = prdPrice;
    }

    public String getPcatName() {
        return pcatName;
    }

    public void setPcatName(String pcatName) {
        this.pcatName = pcatName;
    }

}