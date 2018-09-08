package com.crmmarketing.hmt.GsonModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceSummary implements Parcelable {

@SerializedName("attendancelist")
@Expose
private List<Attendancelist> attendancelist = null;

public List<Attendancelist> getAttendancelist() {
return attendancelist;
}

public void setAttendancelist(List<Attendancelist> attendancelist) {
this.attendancelist = attendancelist;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.attendancelist);
    }

    public AttendanceSummary() {
    }

    protected AttendanceSummary(Parcel in) {
        this.attendancelist = new ArrayList<Attendancelist>();
        in.readList(this.attendancelist, Attendancelist.class.getClassLoader());
    }

    public static final Parcelable.Creator<AttendanceSummary> CREATOR = new Parcelable.Creator<AttendanceSummary>() {
        @Override
        public AttendanceSummary createFromParcel(Parcel source) {
            return new AttendanceSummary(source);
        }

        @Override
        public AttendanceSummary[] newArray(int size) {
            return new AttendanceSummary[size];
        }
    };
}