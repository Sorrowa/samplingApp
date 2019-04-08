package com.example.core.Entity.Data;

public class PointSelectListData {
//    "Id": "00ccdfe8-cdb2-4ea8-a12f-23359220f19d",
//            "Name": "五号点位",
//            "Code": "0005",
//            "Latitude": "14.54",
//            "Longitude": "45.24",
//            "ProjectPointId": "86aafe92-2fcd-4dc8-b19d-1fcc211e2b87",
//            "PointSampPlan": "d9618423-c991-41e3-97df-cb4b844fc2e5"

    private String Id;
    private String Name;
    private String Code;
    private String Latitude;
    private String Longitude;
    private String ProjectPointId;
    private String PointSampPlan;

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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getProjectPointId() {
        return ProjectPointId;
    }

    public void setProjectPointId(String projectPointId) {
        ProjectPointId = projectPointId;
    }

    public String getPointSampPlan() {
        return PointSampPlan;
    }

    public void setPointSampPlan(String pointSampPlan) {
        PointSampPlan = pointSampPlan;
    }
}
