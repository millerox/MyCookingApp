package com.example.mycookingapp.presenter;

import android.app.Activity;

public interface iSignupPresenter {
    void doSignup(Activity activity, String email, String password);
    void doSignupFacebook();
    void doSignupGoogle();
}
