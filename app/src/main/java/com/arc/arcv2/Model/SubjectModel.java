package com.arc.arcv2.Model;

public class SubjectModel {
    private String sub_name;
    private String sub_code;
    private Boolean selected=false;

    public SubjectModel(String sub_name, String sub_code, Boolean selected) {
        this.sub_name = sub_name;
        this.sub_code = sub_code;
        this.selected = selected;
    }

    public SubjectModel() {
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
