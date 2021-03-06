package com.example.mycookingapp.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.mycookingapp.singleton.FirebaseSingleton;
import com.example.mycookingapp.view.iSignupView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignupPresenter extends InputValidation implements iSignupPresenter {

    private iSignupView signupView;
    private FirebaseSingleton firebase;

    public SignupPresenter(iSignupView signupView){
        this.signupView = signupView;
        firebase = FirebaseSingleton.getInstance();
    }

    @Override
    public void doSignup(Activity activity, String name, String email, String password) {
        final int errorCode = isValidData(name,email,password);

        if(errorCode != ERROR_NO_ERRORS){ //There is an error
            showErrorMessage(errorCode);
        }else {
            //TODO PUSH this task to background task & check for internet connection & save user to database
            firebase.authentication.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
        firebase.user = firebase.authentication.getCurrentUser();
        firebase.user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                signupView.onSignupSuccess("Verification link send to " + firebase.user.getEmail()+ ". Confirm your email and login");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signupView.onSignupError("Verification FAILED to sent to " + firebase.user.getEmail());
            }
        });
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
