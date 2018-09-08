package com.crmmarketing.hmt.GsonModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchList implements Parcelable {

@SerializedName("branch")
@Expose
private List<Branch> branch = null;

public List<Branch> getBranch() {
return branch;
}

public void setBranch(List<Branch> branch) {
this.branch = branch;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.branch);
    }

    public BranchList() {
    }

    protected BranchList(Parcel in) {
        this.branch = in.createTypedArrayList(Branch.CREATOR);
    }

    public static final Parcelable.Creator<BranchList> CREATOR = new Parcelable.Creator<BranchList>() {
        @Override
        public BranchList createFromParcel(Parcel source) {
            return new BranchList(source);
        }

        @Override
        public BranchList[] newArray(int size) {
            return new BranchList[size];
        }
    };
}


