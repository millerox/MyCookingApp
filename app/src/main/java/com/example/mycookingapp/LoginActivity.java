package com.example.mycookingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycookingapp.presenter.LoginPresenter;
import com.example.mycookingapp.presenter.iLoginPresenter;
import com.example.mycookingapp.view.iLoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends BasicActivity implements iLoginView {

    private EditText et_userEmail;
    private EditText et_userPsw;
    private TextView tv_errorText;

    private iLoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Initializing views:
        tv_errorText = findViewById(R.id.tv_login_errorText);
        et_userEmail = findViewById(R.id.et_login_userEmail);
        et_userPsw = findViewById(R.id.et_login_psw);

        //Inotializing variebles:
        loginPresenter = new LoginPresenter(this);
    }

    public void onClick_login(View view) {
        Intent intent;
        String email = et_userEmail.getText().toString().trim();
        String password = et_userPsw.getText().toString().trim();

        switch (view.getId()){
            case R.id.btn_login_login:
                loginPresenter.doLogin(LoginActivity.this,email,password);
                break;
            case R.id.btn_login_google:
                loginPresenter.doLoginGoogle();
                break;
            case R.id.btn_login_facebook:
                loginPresenter.doLoginFacebook();
                break;
            case R.id.btn_login_signup:
                //Redirect to SignUp Activity
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login_forgotPsw:
                //Redirect to ResetPassword Activity
                intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onLoginError(String message) {
        Toasty.error(this,message,Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onLoginSuccess(String message) {
        Toasty.success(this,message,Toasty.LENGTH_LONG).show();
        //Go to MyRecipe Page
        Intent intent = new Intent(LoginActivity.this,MyRecipes.class);
        startActivity(intent);
        //TODO Check if email is verified when person wants to add a recipe
    }

    @Override
    public void infoMessage(String message) {
        Toasty.info(this,message,Toasty.LENGTH_LONG).show();
    }
}
