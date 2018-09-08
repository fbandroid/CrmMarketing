package com.crmmarketing.hmt.poductmodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

@SerializedName("pr_list")
@Expose
private List<PrList> prList = null;

public List<PrList> getPrList() {
return prList;
}

public void setPrList(List<PrList> prList) {
this.prList = prList;
}

}