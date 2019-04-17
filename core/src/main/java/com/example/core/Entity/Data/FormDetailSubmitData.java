package com.example.core.Entity.Data;

import java.util.List;

public class FormDetailSubmitData {
    private FormDetailData detail;
    private List<FileDetailData> files;



    public List<FileDetailData> getFiles() {
        return files;
    }

    public void setFiles(List<FileDetailData> files) {
        this.files = files;
    }

    public FormDetailData getDetail() {
        return detail;
    }

    public void setDetail(FormDetailData detail) {
        this.detail = detail;
    }
}
