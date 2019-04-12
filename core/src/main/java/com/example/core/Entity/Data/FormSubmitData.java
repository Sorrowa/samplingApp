package com.example.core.Entity.Data;

import java.util.List;

/**
 * 表单上传
 */
public class FormSubmitData {

    private FormData form;
    private List<FileData> files;
    private boolean submit=false;

    public FormData getForm() {
        return form;
    }

    public void setForm(FormData form) {
        this.form = form;
    }

    public List<FileData> getFiles() {
        return files;
    }

    public void setFiles(List<FileData> files) {
        this.files = files;
    }

    public boolean isSubmit() {
        return submit;
    }

    public void setSubmit(boolean submit) {
        this.submit = submit;
    }

}
