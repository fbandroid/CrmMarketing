package com.crmmarketing.hmt.GsonModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChainInquiry {

@SerializedName("chaindata")
@Expose
private List<Chaindatum> chaindata = null;

public List<Chaindatum> getChaindata() {
return chaindata;
}

public void setChaindata(List<Chaindatum> chaindata) {
this.chaindata = chaindata;
}

}