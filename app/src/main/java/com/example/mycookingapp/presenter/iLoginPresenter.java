package com.example.mycookingapp.presenter;

import android.app.Activity;

public interface iLoginPresenter {
    void clear();
    void doLogin(Activity activity, String email, String password);
    void doLoginFacebook();
    void doLoginGoogle();
}
