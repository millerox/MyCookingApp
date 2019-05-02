package com.example.mycookingapp.model;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;

public class User implements iUser  {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String user_password;
    private String user_email;
    private String user_name;
    private String user_id;

    public User(String user_email, String user_password)
    {
        this.user_email = user_email;
        this.user_password = user_password;
    }

    @Override
    public String getId() {
        return user_id;
    }

    @Override
    public String getEmail() {
        return user_email;
    }

    @Override
    public String getPassword() {
        return user_password;
    }

    @Override
    public String getName() {
        return user_name;
    }


    @Override
    public boolean isEmailVerified() {
        return firebaseAuth.getCurrentUser().isEmailVerified();
    }
}
