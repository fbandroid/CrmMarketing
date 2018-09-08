package com.crmmarketing.hmt.targetmodel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example implements Parcelable {

@SerializedName("gettarget")
@Expose
private List<Gettarget> gettarget = null;

public List<Gettarget> getGettarget() {
return gettarget;
}

public void setGettarget(List<Gettarget> gettarget) {
this.gettarget = gettarget;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.gettarget);
    }

    public Example() {
    }

    protected Example(Parcel in) {
        this.gettarget = in.createTypedArrayList(Gettarget.CREATOR);
    }

    public static final Parcelable.Creator<Example> CREATOR = new Parcelable.Creator<Example>() {
        @Override
        public Example createFromParcel(Parcel source) {
            return new Example(source);
        }

        @Override
        public Example[] newArray(int size) {
            return new Example[size];
        }
    };
}