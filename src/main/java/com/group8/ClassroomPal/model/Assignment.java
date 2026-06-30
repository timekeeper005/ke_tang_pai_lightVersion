package com.group8.ClassroomPal.model;

public class Assignment {

    public String uid;
    public String course_uid;
    public String title;
    public String content_type;
    public String content_data;

    public Assignment() {}

    public Assignment(String uid, String course_uid, String title,
                      String content_type, String content_data) {
        this.uid = uid;
        this.course_uid = course_uid;
        this.title = title;
        this.content_type = content_type;
        this.content_data = content_data;
    }
}
