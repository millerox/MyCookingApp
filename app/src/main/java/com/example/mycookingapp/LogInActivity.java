package com.example.mycookingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends BasicActivity {

    private Button btnLogin;
    private  Button btnSignup;
    private EditText user_email;
    private EditText user_psw;
    private FirebaseAuth user_auth;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Initializing variables:
        errorText = findViewById(R.id.login_errorText);
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup_redirect);
        user_email = findViewById(R.id.reset_email);
        user_psw = findViewById(R.id.user_psw);
        user_auth = FirebaseAuth.getInstance();
    }
    //Method is called if Sign Up Button CLicked
    public void goToSignUpPage(View view)
    {
        Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
    //Method is called if Reset Password link cLicked
    public void goToResetPasswordPage(View view){
        Intent intent = new Intent(LogInActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    public void login(View view){
        final String email = user_email.getText().toString();
        final String password = user_psw.getText().toString();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
        {
            //If fields are NOT EMPTY:
            user_auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                    {
                        Toast.makeText(LogInActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                    }
                    else {
                        //Check if the user confirmed the email
                        if(user_auth.getCurrentUser().isEmailVerified()){
                            //redirect to the main activity
                            errorText.setText("");
                            Intent intent = new Intent(LogInActivity.this, SingleRecipeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            errorText.setText("Please verify your email.");
                        }
                    }
                }
            });
        }
        else{
            //If one of inputs is empty:
            errorText.setText("Enter both email address & password!");
        }
    }
}