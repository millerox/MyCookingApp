package com.example.mycookingapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycookingapp.presenter.LoginPresenter;
import com.example.mycookingapp.presenter.iLoginPresenter;
import com.example.mycookingapp.view.iLoginView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.login.widget.LoginButton;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends BasicActivity implements iLoginView {
    //Views:
    private EditText et_userEmail;
    private EditText et_userPsw;
    private TextView tv_errorText;
    //Interface:
    private iLoginPresenter loginPresenter;
    //Google
    private  GoogleSignInOptions mGoogleSignInOptions;
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN =2;
    //Facebook
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Initializing views:
        tv_errorText = findViewById(R.id.tv_login_errorText);
        et_userEmail = findViewById(R.id.et_login_userEmail);
        et_userPsw = findViewById(R.id.et_login_psw);
        //Initializing variables:
        loginPresenter = new LoginPresenter(this);
        //Google
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);
        //Facebook
        mCallbackManager = CallbackManager.Factory.create();
    }

    public void onClick_login(View view) {
        String email = et_userEmail.getText().toString().trim();
        String password = et_userPsw.getText().toString().trim();

        switch (view.getId()){
            case R.id.btn_login_login:
                loginPresenter.doLogin(LoginActivity.this, email, password);
                break;
            case R.id.btn_login_google:
                loginPresenter.doLoginGoogle(LoginActivity.this, mGoogleSignInClient, RC_SIGN_IN);
                break;
            case R.id.btn_login_facebook:
                loginPresenter.doLoginFacebook(findViewById(R.id.btn_login_facebook),mCallbackManager);
                break;
            case R.id.btn_login_signup:
                redirectToActivity(this,SignUpActivity.class);
                break;
            case R.id.tv_login_forgotPsw:
                redirectToActivity(this,ResetPasswordActivity.class);
                break;
        }
    }
    // EMAIL & PASSWORD LOGIN RESULT METHODS:
    @Override
    public void onLoginError(String message) {
        Toasty.error(this,message,Toasty.LENGTH_LONG).show();
    }

    @Override
    public void onLoginSuccess(String message) {
        Toasty.success(this,message,Toasty.LENGTH_LONG).show();
        redirectToActivity(this,MyRecipes.class);
        //TODO Check if email is verified when person wants to add a recipe
    }

    @Override
    public void infoMessage(String message) {
        Toasty.info(this,message,Toasty.LENGTH_LONG).show();
    }

    //Social Login on Activity Results
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) { //Handle Google Sign In
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            loginPresenter.handleGoogleSignInResult(task);
        }else{ //Handle Facebook Sign in
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
