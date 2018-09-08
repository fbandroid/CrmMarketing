package com.crmmarketing.hmt.gsonmodel33;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EcsList {

@SerializedName("getinquiry")
@Expose
private List<Getinquiry> getinquiry = null;

public List<Getinquiry> getGetinquiry() {
return getinquiry;
}

public void setGetinquiry(List<Getinquiry> getinquiry) {
this.getinquiry = getinquiry;
}

}