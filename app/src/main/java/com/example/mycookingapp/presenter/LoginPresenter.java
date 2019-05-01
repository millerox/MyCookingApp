package com.example.mycookingapp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.example.mycookingapp.LoginActivity;
import com.example.mycookingapp.MyRecipes;
import com.example.mycookingapp.R;
import com.example.mycookingapp.SignUpActivity;
import com.example.mycookingapp.SingleRecipeActivity;
import com.example.mycookingapp.model.User;
import com.example.mycookingapp.model.iUser;
import com.example.mycookingapp.view.iLoginView;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.logging.Handler;

public class LoginPresenter implements iLoginPresenter{

    private iLoginView loginView;
    private FirebaseAuth firebaseAuth;
    //Error codes:
    private final int NO_ERRORS = 0;
    private final int ERROR_FIELDS_EMPTY = 1;
    private final int ERROR_EMAIL_PATTERN = 2;
    private final int ERROR_PASSWORD_LENGTH = 3;
    private final int ERROR_FIREBASE = 4;

    public LoginPresenter(iLoginView loginView){
        this.loginView = loginView;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void doLogin(Activity activity, String email, String password) {
        final int errorCode = isValidData(email,password);

        if(errorCode != 0){ //There is an error
            showErrorMessage(errorCode);
        }else {
            //TODO Firebase Login here, check for internet connection
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        showErrorMessage(ERROR_FIREBASE);
                    }
                    else {
                        loginView.onLoginSuccess("Login Success");
                    }
                }
            });
        }
    }

    public int isValidData(String email, String password) {
        // 1.Check if fields are empty
        // 2.Check if email matches pattern
        // 3.Check if password length > 6
        int errorType = NO_ERRORS;
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            errorType = ERROR_FIELDS_EMPTY;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            errorType = ERROR_EMAIL_PATTERN;
        }else if (password.length() < 6){
            errorType = ERROR_PASSWORD_LENGTH;
        }
        return errorType;
    }

    public void showErrorMessage(int errorCode){
        switch (errorCode){
            case ERROR_FIELDS_EMPTY:
                loginView.onLoginError("Email & Password can't be empty");
                break;
            case ERROR_EMAIL_PATTERN:
                loginView.onLoginError("Email is not valid");
                break;
            case ERROR_PASSWORD_LENGTH:
                loginView.onLoginError("Password must be at least 6 characters long");
                break;
            case ERROR_FIREBASE:
                loginView.onLoginError("Login Failed");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isEmailVerified() {
        return firebaseAuth.getCurrentUser().isEmailVerified();
    }

    @Override
    public void doLoginFacebook() {

    }

    @Override
    public void doLoginGoogle() {

    }

    @Override
    public void clear() {

    }

}