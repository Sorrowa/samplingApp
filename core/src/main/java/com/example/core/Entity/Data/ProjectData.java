package com.example.core.Entity.Data;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 待采样与已采样
 * 获取项目列表的DataBean
 */
@SuppressLint("SimpleDateFormat")
public class ProjectData implements Parcelable {

//    "Id": "f1d85c28-95a0-4bc4-b714-0f4a3ffb0b00",
//    "ProjectName": "测试采样单任务",
//    "BeginTime": "2019-03-20 00:00:00",
//    "EndTime": "2019-05-04 00:00:00",
//    "TotalPoint": 4,
//    "SampCount": 1,
//    "QuaCount": 1

    private String Id;
    private String ProjectName;
    private String BeginTime;
    private String EndTime;
    private String TotalPoint;
    private String SampCount;
    private String QuaCount;


    private ProjectData(Parcel in){
        Id=in.readString();
        ProjectName=in.readString();
        BeginTime=in.readString();
        EndTime=in.readString();
        TotalPoint=in.readString();
        SampCount=in.readString();
        QuaCount=in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(ProjectName);
        parcel.writeString(BeginTime);
        parcel.writeString(EndTime);
        parcel.writeString(TotalPoint);
        parcel.writeString(SampCount);
        parcel.writeString(QuaCount);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getBeginTime() {
        return BeginTime.toString();
    }



    public String getEndTime() {
        return EndTime.toString();
    }



    public String getTotalPoint() {
        return TotalPoint;
    }

    public void setTotalPoint(String totalPoint) {
        TotalPoint = totalPoint;
    }

    public String getSampCount() {
        return SampCount;
    }

    public void setSampCount(String sampCount) {
        SampCount = sampCount;
    }

    public String getQuaCount() {
        return QuaCount;
    }

    public void setQuaCount(String quaCount) {
        QuaCount = quaCount;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProjectData> CREATOR = new Creator<ProjectData>() {
        @Override
        public ProjectData createFromParcel(Parcel in) {
            return new ProjectData(in);
        }

        @Override
        public ProjectData[] newArray(int size) {
            return new ProjectData[size];
        }
    };

}
