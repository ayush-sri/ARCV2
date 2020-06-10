package com.arc.arcv2.Model;

public class User {
    private String user_phone;
    private String user_type;
    private String user_name;
    private String user_email;
    private String user_id;
    private SubjectModel subject;

    public User() {

    }

    public SubjectModel getSubject() {
        return subject;
    }

    public void setSubject(SubjectModel subject) {
        this.subject = subject;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public User(String user_phone, String user_type, String user_name, String user_email, String user_id,SubjectModel subject) {
        this.user_phone = user_phone;
        this.user_type = user_type;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_id = user_id;
        this.subject = subject;
    }
}
