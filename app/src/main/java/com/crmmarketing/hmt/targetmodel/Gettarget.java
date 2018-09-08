package com.crmmarketing.hmt.targetmodel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gettarget implements Parcelable {

@SerializedName("tar_master")
@Expose
private TarMaster tarMaster;
@SerializedName("tar_child")
@Expose
private List<TarChild> tarChild = null;

public TarMaster getTarMaster() {
return tarMaster;
}

public void setTarMaster(TarMaster tarMaster) {
this.tarMaster = tarMaster;
}

public List<TarChild> getTarChild() {
return tarChild;
}

public void setTarChild(List<TarChild> tarChild) {
this.tarChild = tarChild;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.tarMaster, flags);
        dest.writeTypedList(this.tarChild);
    }

    public Gettarget() {
    }

    protected Gettarget(Parcel in) {
        this.tarMaster = in.readParcelable(TarMaster.class.getClassLoader());
        this.tarChild = in.createTypedArrayList(TarChild.CREATOR);
    }

    public static final Parcelable.Creator<Gettarget> CREATOR = new Parcelable.Creator<Gettarget>() {
        @Override
        public Gettarget createFromParcel(Parcel source) {
            return new Gettarget(source);
        }

        @Override
        public Gettarget[] newArray(int size) {
            return new Gettarget[size];
        }
    };
}