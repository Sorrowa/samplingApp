package com.example.core.Entity.Data;

public class FileData {
//    "FileType":4,
//    "FileName":"左中右框架模板标注_PxCook",
//    "FilePath":"/Upload/20190321/ReName_左中右框架模板标注_PxCook-副本(2)-副本(1).png"

    private String FileType;
    private String FileName;
    private String FilePath;

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
