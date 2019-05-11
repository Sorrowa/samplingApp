package com.example.core.Entity.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 通过id获取点位详情数据
 */
public class PointDetailByIdData implements Parcelable {

    /**
     * Id : 00ccdfe8-cdb2-4ea8-a12f-23359220f19d
     * Name : 五号点位
     * Code : 0005
     * Latitude : 30.590515
     * Longitude : 104.134615
     */

    private String Id;
    private String Name;
    private String Code;
    private String Latitude;
    private String Longitude;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Id);
        dest.writeString(this.Name);
        dest.writeString(this.Code);
        dest.writeString(this.Latitude);
        dest.writeString(this.Longitude);
    }

    public PointDetailByIdData() {
    }

    protected PointDetailByIdData(Parcel in) {
        this.Id = in.readString();
        this.Name = in.readString();
        this.Code = in.readString();
        this.Latitude = in.readString();
        this.Longitude = in.readString();
    }

    public static final Creator<PointDetailByIdData> CREATOR = new Creator<PointDetailByIdData>() {
        @Override
        public PointDetailByIdData createFromParcel(Parcel source) {
            return new PointDetailByIdData(source);
        }

        @Override
        public PointDetailByIdData[] newArray(int size) {
            return new PointDetailByIdData[size];
        }
    };
}
