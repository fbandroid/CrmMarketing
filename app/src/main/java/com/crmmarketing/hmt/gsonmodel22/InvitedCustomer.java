package com.crmmarketing.hmt.gsonmodel22;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvitedCustomer {

@SerializedName("reports")
@Expose
private List<Report> reports = null;

public List<Report> getReports() {
return reports;
}

public void setReports(List<Report> reports) {
this.reports = reports;
}

}