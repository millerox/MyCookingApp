package com.example.mycookingapp.model;

import com.google.firebase.auth.FirebaseAuth;

public class User implements iUser  {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String user_name;
    private String user_id;

    public User(String user_id, String user_name)
    {
        this.user_id = user_id;
        this.user_name = user_name;
    }

    @Override
    public String getId() {
        return user_id;
    }


    @Override
    public String getName() {
        return user_name;
    }


    @Override
    public boolean isEmailVerified() {
        return firebaseAuth.getCurrentUser().isEmailVerified();
    }

    @Override
    public boolean hasRecipes() {
        return false;
    }
}
