package com.group8.ClassroomPal.model;

public class Submission {
    public String id;
    public String assignment_uid;
    public String student_uid;
    public String content;
    public int score;
    public String feedback;
    public String status;

    public Submission() {}

    public Submission(String id, String assignment_uid, String student_uid, String content,
                      int score, String feedback, String status) {
        this.id = id;
        this.assignment_uid = assignment_uid;
        this.student_uid = student_uid;
        this.content = content;
        this.score = score;
        this.feedback = feedback;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignment_uid() {
        return assignment_uid;
    }

    public void setAssignment_uid(String assignment_uid) {
        this.assignment_uid = assignment_uid;
    }

    public String getStudent_uid() {
        return student_uid;
    }

    public void setStudent_uid(String student_uid) {
        this.student_uid = student_uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
