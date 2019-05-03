package com.example.mycookingapp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.mycookingapp.LoginActivity;
import com.example.mycookingapp.SignUpActivity;
import com.example.mycookingapp.model.User;
import com.example.mycookingapp.view.iLoginView;
import com.example.mycookingapp.view.iSignupView;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupPresenter extends InputValidation implements iSignupPresenter {

    private iSignupView signupView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser current_user;

    public SignupPresenter(iSignupView signupView){
        this.signupView = signupView;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void doSignup(Activity activity, String name, String email, String password) {
        final int errorCode = isValidData(name,email,password);

        if(errorCode != ERROR_NO_ERRORS){ //There is an error
            showErrorMessage(errorCode);
        }else {
            //TODO PUSH this task to background task & check for internet connection & save user to database
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        showErrorMessage(ERROR_FIREBASE);
                    } else {
                        sendEmailVerification();
                    }
                }
            });
        }
    }

    public void showErrorMessage(int errorCode) {
        switch (errorCode) {
            case ERROR_FIELDS_EMPTY:
                signupView.onSignupError("Name & Email & Password can't be empty");
                break;
            case ERROR_EMAIL_PATTERN:
                signupView.onSignupError("Email is not valid");
                break;
            case ERROR_PASSWORD_LENGTH:
                signupView.onSignupError("Password must be at least 6 characters long");
                break;
            case ERROR_FIREBASE:
                signupView.onSignupError("Signup Failed");
                break;
            default:
                break;
        }
    }

    @Override
    public void sendEmailVerification() {
        current_user = firebaseAuth.getCurrentUser();
        current_user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                signupView.onSignupSuccess("Verification link send to " + current_user.getEmail()+ ". Confirm your email and login");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signupView.onSignupError("Verification FAILED to sent to " + current_user.getEmail());
            }
        });
    }

    @Override
    public void doSignupGoogle() {

    }

    @Override
    public void doSignupFacebook() {

    }

//    public void saveUserToDatabase(View view) // To Database
//    {
//        boolean verified = false;
//
//        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) ){
//            String id = firebaseReference.push().getKey();
//            User user = new User(email,password);
//            firebaseReference.child(id).setValue(user);
//            //Clear the input fields
//            et_userName.setText("");
//            et_userEmail.setText("");
//            et_userPsw.setText("");
//        }else {
//            Toast.makeText(SignUpActivity.this,"Field(s) are empty",Toast.LENGTH_LONG).show();
//        }
//    }
}
