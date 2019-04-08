package com.example.core.Entity.Data;

/**
 * 样品
 */
public class SamplingData {
//    "SampCode": "2019032110002",
//            "MonNames": "总磷",
//            "SampTypeName": "测试样"

    private String SampCode;
    private String MonNames;
    private String SampTypeName;

    public String getSampCode() {
        return SampCode;
    }

    public void setSampCode(String sampCode) {
        SampCode = sampCode;
    }

    public String getMonNames() {
        return MonNames;
    }

    public void setMonNames(String monNames) {
        MonNames = monNames;
    }

    public String getSampTypeName() {
        return SampTypeName;
    }

    public void setSampTypeName(String sampTypeName) {
        SampTypeName = sampTypeName;
    }
}
