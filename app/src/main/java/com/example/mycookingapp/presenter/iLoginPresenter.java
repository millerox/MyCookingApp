package com.example.mycookingapp.presenter;

import android.app.Activity;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;

public interface iLoginPresenter {
    void doLogin(Activity activity, String email, String password);
    void doLoginGoogle(Activity activity, GoogleSignInClient client, int resultCode);
    void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask);
    void doLoginFacebook(View button, CallbackManager manager);
    void handleFacebookSignInResult(AccessToken token);
}
