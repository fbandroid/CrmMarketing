package com.crmmarketing.hmt.gsonmodel22;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TravelReport {

@SerializedName("traveldata")
@Expose
private List<Traveldatum> traveldata = null;

public List<Traveldatum> getTraveldata() {
return traveldata;
}

public void setTraveldata(List<Traveldatum> traveldata) {
this.traveldata = traveldata;
}

}