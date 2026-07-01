package com.group8.ClassroomPal.model;

public class Course {
    public String uid;
    public String name;
    public String sub_title;
    public String description;
    public String school;
    public String semester;
    public String cover_image;
    public String status;

    public Course() {}

    public Course(String uid, String name, String sub_title, String description,
                  String school, String semester, String cover_image, String status) {
        this.uid = uid;
        this.name = name;
        this.sub_title = sub_title;
        this.description = description;
        this.school = school;
        this.semester = semester;
        this.cover_image = cover_image;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
