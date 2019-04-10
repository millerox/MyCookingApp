package com.example.mycookingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    EditText email;
    EditText user_name;
    EditText user_psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.signup_email);
        user_name = findViewById(R.id.signup_name);
        user_psw = findViewById(R.id.signup_password);
    }

    public void createUser(View view)
    {

    }
}
