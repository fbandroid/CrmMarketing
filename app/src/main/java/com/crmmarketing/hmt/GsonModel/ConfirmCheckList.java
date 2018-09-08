package com.crmmarketing.hmt.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by USER on 30-05-2017.
 */

public class ConfirmCheckList {

    @SerializedName("list")
    @Expose
    private java.util.List<CheckListModel> list = null;

    public java.util.List<CheckListModel> getList() {
        return list;
    }

    public void setList(java.util.List<CheckListModel> list) {
        this.list = list;
    }

}
