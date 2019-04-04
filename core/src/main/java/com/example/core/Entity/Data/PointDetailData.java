package com.example.core.Entity.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 点位列表数据
 */
public class PointDetailData implements Parcelable {
//    "Id": "00ccdfe8-cdb2-4ea8-a12f-23359220f19d",
//            "Name": "五号点位",
//            "Longitude": "45.24",
//            "Latitude": "14.54",
//            "Status": 1,
//            "StatusName": "已采样"

    private String Id;
    private String Name;
    private String Longitude;
    private String Latitude;
    private String Status;
    private String StatusName;

    private PointDetailData(Parcel in){
        Id=in.readString();
        Name=in.readString();
        Longitude=in.readString();
        Latitude=in.readString();
        Status=in.readString();
        StatusName=in.readString();
    }

    public static final Creator<PointDetailData> CREATOR = new Creator<PointDetailData>() {
        @Override
        public PointDetailData createFromParcel(Parcel in) {
            return new PointDetailData(in);
        }

        @Override
        public PointDetailData[] newArray(int size) {
            return new PointDetailData[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Name);
        parcel.writeString(Longitude);
        parcel.writeString(Latitude);
        parcel.writeString(Status);
        parcel.writeString(StatusName);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
