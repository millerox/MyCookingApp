package com.example.mycookingapp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.example.mycookingapp.R;
import com.example.mycookingapp.model.User;
import com.example.mycookingapp.view.iLoginView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.net.InterfaceAddress;

public class LoginPresenter extends InputValidation implements iLoginPresenter {

    private iLoginView loginView;
    private FirebaseAuth firebaseAuth;

    public LoginPresenter(iLoginView loginView){
        this.loginView = loginView;
        firebaseAuth = FirebaseAuth.getInstance();
    }
    // EMAIL & PASSWORD LOGIN
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
    //GOOGLE SIGN IN
    @Override
    public void doLoginGoogle(Activity activity, GoogleSignInClient client, int RC_SIGN_IN) {
        Intent signInIntent = client.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try { // Google Signed in successfully, authenticate with Firebase.
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            loginView.onLoginError("Google Sign is Failed");
        }
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            loginView.onLoginSuccess("Hello, " + user.getDisplayName());
                        } else {
                            // If sign in fails, display a message to the user.
                            loginView.onLoginError("Google Sign in Error");
                        }
                    }
                });
    }

    @Override
    public void doLoginFacebook() {

    }

    @Override
    public void clear() {

    }
}