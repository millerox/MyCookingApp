package com.example.mycookingapp.singleton;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseSingleton {

    private static FirebaseSingleton firebaseSingleton = null;

    public FirebaseDatabase database;
    public FirebaseAuth authentication;
    public DatabaseReference databaseReference;
    public FirebaseUser user;
    public AuthCredential credential;

    private FirebaseSingleton(){
        database = FirebaseDatabase.getInstance();
        authentication = FirebaseAuth.getInstance();
    }

    public static FirebaseSingleton getInstance(){
        if(firebaseSingleton == null){
            firebaseSingleton = new FirebaseSingleton();
        }
        return firebaseSingleton;
    }



}
