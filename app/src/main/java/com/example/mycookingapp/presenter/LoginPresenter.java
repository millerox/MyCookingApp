package com.example.mycookingapp.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mycookingapp.model.User;
import com.example.mycookingapp.view.iLoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.net.InterfaceAddress;

public class LoginPresenter extends InputValidation implements iLoginPresenter {

    private iLoginView loginView;
    private FirebaseAuth firebaseAuth;

    public LoginPresenter(iLoginView loginView){
        this.loginView = loginView;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void doLogin(Activity activity, String email, String password) {
        final int errorCode = isValidData(email,password);

        if(errorCode != ERROR_NO_ERRORS){ //There is an error
            showErrorMessage(errorCode);
        }else {
            //TODO check for internet connection
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Log.e("debug", "onComplete: Failed=" + task.getException().getMessage());
                        showErrorMessage(ERROR_FIREBASE);
                    }else {
                        loginView.onLoginSuccess("Login Success");
                    }
                }
            });
        }
    }

    @Override
    public void doLoginFacebook() {

    }

    @Override
    public void doLoginGoogle() {

    }

    public void showErrorMessage(int errorCode) {
        switch (errorCode) {
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
    public void clear() {

    }
}