package com.example.core.Entity.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class PointData implements Parcelable {

//    "Id": "19591b25-6e23-404d-bef7-2e22f11b10d5",
//            "PointName": "五号点位",
//            "Status": 1,
//            "StatusName": "已提交",
//            "ActSamper": "张三,李四,王五",
//            "ActSampTime": "2019-03-22 09:54:00",
//            "PointId": "00ccdfe8-cdb2-4ea8-a12f-23359220f19d"

    private String Id;
    private String PointName;
    private String Status;
    private String StatusName;
    private String ActSamper;
    private String ActSampTime;
    private String PointId;


    PointData(Parcel in){
        Id=in.readString();
        PointName=in.readString();
        Status=in.readString();
        StatusName=in.readString();
        ActSamper=in.readString();
        ActSampTime=in.readString();
        PointId=in.readString();
    }

    public static final Creator<PointData> CREATOR = new Creator<PointData>() {
        @Override
        public PointData createFromParcel(Parcel in) {
            return new PointData(in);
        }

        @Override
        public PointData[] newArray(int size) {
            return new PointData[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(PointName);
        parcel.writeString(Status);
        parcel.writeString(StatusName);
        parcel.writeString(ActSamper);
        parcel.writeString(ActSampTime);
        parcel.writeString(PointId);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPointName() {
        return PointName;
    }

    public void setPointName(String pointName) {
        PointName = pointName;
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

    public String getActSamper() {
        return ActSamper;
    }

    public void setActSamper(String actSamper) {
        ActSamper = actSamper;
    }

    public String getActSampTime() {
        return ActSampTime;
    }

    public void setActSampTime(String actSampTime) {
        ActSampTime = actSampTime;
    }

    public String getPointId() {
        return PointId;
    }

    public void setPointId(String pointId) {
        PointId = pointId;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
