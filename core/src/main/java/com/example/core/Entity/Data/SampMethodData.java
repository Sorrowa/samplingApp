package com.example.core.Entity.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class SampMethodData implements Parcelable {
//    "SampMethod": "a39553dd-cc13-4ceb-8072-beb21c37c54a",
//            "SampMethodName": "测试采样方法",
//            "SampMethodCode": "001"

    private String SampMethod;
    private String SampMethodName;
    private String SampMethodCode;


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(SampMethod);
        parcel.writeString(SampMethodName);
        parcel.writeString(SampMethodCode);
    }

    private SampMethodData(Parcel in){
        SampMethod = in.readString();
        SampMethodName = in.readString();
        SampMethodCode = in.readString();
    }

    public static final Creator<SampMethodData> CREATOR = new Creator<SampMethodData>() {
        @Override
        public SampMethodData createFromParcel(Parcel in) {
            return new SampMethodData(in);
        }

        @Override
        public SampMethodData[] newArray(int size) {
            return new SampMethodData[size];
        }
    };

    public String getSampMethod() {
        return SampMethod;
    }

    public void setSampMethod(String sampMethod) {
        SampMethod = sampMethod;
    }

    public String getSampMethodName() {
        return SampMethodName;
    }

    public void setSampMethodName(String sampMethodName) {
        SampMethodName = sampMethodName;
    }

    public String getSampMethodCode() {
        return SampMethodCode;
    }

    public void setSampMethodCode(String sampMethodCode) {
        SampMethodCode = sampMethodCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
