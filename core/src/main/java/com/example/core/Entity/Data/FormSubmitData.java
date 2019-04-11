package com.example.core.Entity.Data;

import java.util.List;

/**
 * 表单上传
 */
public class FormSubmitData {

    private FormData form;
    private List<FileInfo> files;
    private boolean submit=true;

    public FormData getForm() {
        return form;
    }

    public void setForm(FormData form) {
        this.form = form;
    }

    public List<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfo> files) {
        this.files = files;
    }

    public boolean isSubmit() {
        return submit;
    }

    public void setSubmit(boolean submit) {
        this.submit = submit;
    }


    class FileInfo{
//        "FileType":0,
//        "FileName":"左中右框架模板标注_PxCook",
//        "FilePath":"/Upload/20190321/ReName_左中右框架模板标注_PxCook-副本(2)-副本(1).png"

        private String FileType;
        private String FileName;
        private String FilePath;
    }
}
