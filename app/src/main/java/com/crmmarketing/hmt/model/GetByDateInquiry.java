package com.crmmarketing.hmt.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by USER on 06-05-2017.
 */

public class GetByDateInquiry {


    public GetByDateInquiry(){}
    public ArrayList<InqMaster> getGetinquiry() {
        return getinquiry;
    }

    public void setGetinquiry(ArrayList<InqMaster> getinquiry) {
        this.getinquiry = getinquiry;
    }

    @SerializedName("getinquiry")
    private ArrayList<InqMaster> getinquiry;

}
