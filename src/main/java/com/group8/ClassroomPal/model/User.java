package com.group8.ClassroomPal.model;

public class User {
    public String uid;
    public String name;
    public String role;

    public User() {}

    public User(String uid, String name, String role) {
        this.uid = uid;
        this.name = name;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
