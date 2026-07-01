package com.group8.ClassroomPal.model;

public class Membership {
    public String course_uid;
    public String user_uid;
    public String role;

    public Membership() {}

    public Membership(String course_uid, String user_uid, String role) {
        this.course_uid = course_uid;
        this.user_uid = user_uid;
        this.role = role;
    }

    public String getCourse_uid() {
        return course_uid;
    }

    public void setCourse_uid(String course_uid) {
        this.course_uid = course_uid;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
