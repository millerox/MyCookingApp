package com.example.mycookingapp;

public class User {

    private String user_id;
    private String user_name;
    private String user_password;
    private String user_email;
    private boolean verified;


    public User(String user_id, String user_name, String user_password, String user_email, boolean verified)
    {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_email = user_email;
        this.verified = verified;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public boolean isVerified() {
        return verified;
    }

    public User(){

    }
}
