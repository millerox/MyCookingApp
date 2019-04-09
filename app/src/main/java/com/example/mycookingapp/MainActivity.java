package com.example.mycookingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnSignup;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing variables:
        btnLogin = findViewById(R.id.btn_Login);
        btnSignup = findViewById(R.id.btn_Signup);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_psw);
    }
}
