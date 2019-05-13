package com.example.mycookingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycookingapp.model.User;
import com.example.mycookingapp.presenter.SignupPresenter;
import com.example.mycookingapp.presenter.iSignupPresenter;
import com.example.mycookingapp.view.iSignupView;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends BasicActivity implements iSignupView {

    private EditText et_userName;
    private EditText et_userEmail;
    private EditText et_userPsw;

    private iSignupPresenter signupPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initializing views:
        et_userName = findViewById(R.id.et_signup_userName);
        et_userEmail = findViewById(R.id.et_signup_userEmail);
        et_userPsw = findViewById(R.id.et_signup_userPsw);
        //Initializing variables:
        signupPresenter = new SignupPresenter(this);
    }

    public void onClick_signup(View view) {
        String name = et_userName.getText().toString();
        String email = et_userEmail.getText().toString().trim();
        String password = et_userPsw.getText().toString().trim();
        switch (view.getId()){
            case R.id.btn_signup_signup:
                signupPresenter.doSignup(this,name,email,password);
                break;
            case R.id.btn_signup_google:
               // signupPresenter.doSignupGoogle();
                break;
            case R.id.btn_signup_facebook:
               // signupPresenter.doSignupFacebook();
                break;
        }
    }

    @Override
    public void onSignupError(String message) {
        Toasty.error(this,message,Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onSignupSuccess(String message) {
        Toasty.success(this,message,Toasty.LENGTH_LONG).show();
        //Go to Login Page
        redirectToActivity(SignUpActivity.this, LoginActivity.class);
    }
}
