package com.group8.ClassroomPal.model;

public class Credential {
    public String uid;
    public String email;
    public String password_hash;

    public Credential() {}

    public Credential(String uid, String email, String password_hash) {
        this.uid = uid;
        this.email = email;
        this.password_hash = password_hash;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
}
