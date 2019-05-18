package com.example.mycookingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycookingapp.singleton.FirebaseSingleton;
import com.google.firebase.auth.FirebaseAuth;

public class frgMyRecipes extends Fragment {

    private FirebaseSingleton firebase;
    private FirebaseAuth firebaseAuth;
    private TextView tv_msg;
    private Button btn_login_or_add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_my_recipes_empty, container, false);
        //Initialize views
        tv_msg = view.findViewById(R.id.tv_myRecipes_msg);
        btn_login_or_add = view.findViewById(R.id.btn_myRecipes_redirect);
        //Initialite variables
        firebase = FirebaseSingleton.getInstance();
        firebaseAuth = firebase.authentication;

        if(firebaseAuth.getCurrentUser() == null){
            tv_msg.setText("@string/msg_only_registered_user");
            btn_login_or_add.setText("@sting/btn_login");
        }else{
            //TODO if user has recipes: display recipes layout
        }
        return view;
    }

    public void redirect(View view) {
        if(firebaseAuth.getCurrentUser() == null){
           //redirectToActivity(this, LoginActivity.class);
        }else{
           // redirectToActivity(this, AddRecipe.class);
        }
    }
}
