package com.example.kimhyonseong.thirdthing;

public class User {

    public String password;
    public String email;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
