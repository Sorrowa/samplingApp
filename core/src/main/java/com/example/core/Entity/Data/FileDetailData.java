package com.example.core.Entity.Data;

public class FileDetailData {
//    "Id": "4c206911-9fcc-473c-8edf-20dab9f2f0f5",
//            "SampFormId": "19591b25-6e23-404d-bef7-2e22f11b10d5",
//            "FileType": 2,
//            "FileName": "左中右框架模板标注_PxCook",
//            "FilePath": "/Upload/20190321/ReName_左中右框架模板标注_PxCook-副本(2)-副本(1).png"

//    环境照片=0,
//    采样照片=1,
//    样品照片=2,
//    采样视频=3,
    private String Id;
    private String SampFormId;
    private String FileType;
    private String FileName;
    private String FilePath;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSampFormId() {
        return SampFormId;
    }

    public void setSampFormId(String sampFormId) {
        SampFormId = sampFormId;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String fileType) {
        FileType = fileType;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }
}
