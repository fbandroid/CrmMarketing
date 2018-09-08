package com.crmmarketing.hmt.GsonModel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Getinquiry {

    @SerializedName("inq_master")
    @Expose
    private InqMaster inqMaster;
    @SerializedName("cus_info")
    @Expose
    private CusInfo cusInfo;
    @SerializedName("inq_child")
    @Expose
    private List<InqChild> inqChild = null;

    public InqMaster getInqMaster() {
        return inqMaster;
    }

    public void setInqMaster(InqMaster inqMaster) {
        this.inqMaster = inqMaster;
    }

    public CusInfo getCusInfo() {
        return cusInfo;
    }

    public void setCusInfo(CusInfo cusInfo) {
        this.cusInfo = cusInfo;
    }

    public List<InqChild> getInqChild() {
        return inqChild;
    }

    public void setInqChild(List<InqChild> inqChild) {
        this.inqChild = inqChild;
    }

}