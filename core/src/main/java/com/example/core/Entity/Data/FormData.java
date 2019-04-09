package com.example.core.Entity.Data;

/**
 * 表单基础数据部分
 */
public class FormData {
//    "Id":"19591B25-6E23-404D-BEF7-2E22F11B10D5",
//            "PointSampPlan":"d9618423-c991-41e3-97df-cb4b844fc2e5",
//            "ProjectPointId":"86aafe92-2fcd-4dc8-b19d-1fcc211e2b87",
//            "ActSampTime":"2019-03-22 09:54:00",
//            "ActSamper":"张三,李四,王五",
//            "PointSatus":0,
//            "SampMethod":"测试采样方法",
//            "Weather":"多云",
//            "Tempature":"20℃",
//            "GTempature":"30℃",
//            "Pressure":"10kpa",
//            "TransMethod":"汽运",
//            "ActLongitude":"101.3624",
//            "ActLatitude":"31.0236",
//            "Distance":"25",
//            "SampDesc":"测试采样单备注"

    private String Id;
    private String PointSampPlan;
    private String ProjectPointId;
    private String ActSampTime;
    private String ActSamper;
    private String PointSatus;
    private String SampMethod;
    private String Weather;
    private String Tempature;
    private String GTempature;
    private String Pressure;
    private String TransMethod;
    private double ActLongitude;
    private double ActLatitude;
    private String Distance;
    private String SampDesc;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPointSampPlan() {
        return PointSampPlan;
    }

    public void setPointSampPlan(String pointSampPlan) {
        PointSampPlan = pointSampPlan;
    }

    public String getProjectPointId() {
        return ProjectPointId;
    }

    public void setProjectPointId(String projectPointId) {
        ProjectPointId = projectPointId;
    }

    public String getActSampTime() {
        return ActSampTime;
    }

    public void setActSampTime(String actSampTime) {
        ActSampTime = actSampTime;
    }

    public String getActSamper() {
        return ActSamper;
    }

    public void setActSamper(String actSamper) {
        ActSamper = actSamper;
    }

    public String getPointSatus() {
        return PointSatus;
    }

    public void setPointSatus(String pointSatus) {
        PointSatus = pointSatus;
    }

    public String getSampMethod() {
        return SampMethod;
    }

    public void setSampMethod(String sampMethod) {
        SampMethod = sampMethod;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String weather) {
        Weather = weather;
    }

    public String getTempature() {
        return Tempature;
    }

    public void setTempature(String tempature) {
        Tempature = tempature;
    }

    public String getGTempature() {
        return GTempature;
    }

    public void setGTempature(String GTempature) {
        this.GTempature = GTempature;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getTransMethod() {
        return TransMethod;
    }

    public void setTransMethod(String transMethod) {
        TransMethod = transMethod;
    }


    public void setActLatitude(double actLatitude) {
        ActLatitude = actLatitude;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getSampDesc() {
        return SampDesc;
    }

    public void setSampDesc(String sampDesc) {
        SampDesc = sampDesc;
    }

    public double getActLongitude() {
        return ActLongitude;
    }

    public void setActLongitude(double actLongitude) {
        ActLongitude = actLongitude;
    }

    public double getActLatitude() {
        return ActLatitude;
    }
}
