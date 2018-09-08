package com.crmmarketing.hmt.GsonModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckListAll {

@SerializedName("checklist")
@Expose
private List<Checklist> checklist = null;

public List<Checklist> getChecklist() {
return checklist;
}

public void setChecklist(List<Checklist> checklist) {
this.checklist = checklist;
}

}