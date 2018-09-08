package com.crmmarketing.hmt.gsonmodel22;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolumeData {

@SerializedName("data")
@Expose
private List<Datum> data = null;

public List<Datum> getData() {
return data;
}

public void setData(List<Datum> data) {
this.data = data;
}

}