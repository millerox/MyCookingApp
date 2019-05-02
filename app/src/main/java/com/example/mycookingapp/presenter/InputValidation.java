package com.example.mycookingapp.presenter;

import android.text.TextUtils;
import android.util.Patterns;

public class InputValidation {
    final int FIREBASE_PASSWORD_STANDART = 6;
    //Error codes:
    final int ERROR_NO_ERRORS = 0;
    final int ERROR_FIELDS_EMPTY = 1;
    final int ERROR_EMAIL_PATTERN = 2;
    final int ERROR_PASSWORD_LENGTH = 3;
    final int ERROR_FIREBASE = 4;

    protected int isValidData(String email,String password) {
        //Validation for Login form
        // 1.Check if fields are empty
        // 2.Check if email matches pattern
        // 3.Check if password length > 6
        int errorType = ERROR_NO_ERRORS;
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            errorType = ERROR_FIELDS_EMPTY;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            errorType = ERROR_EMAIL_PATTERN;
        }else if (password.length() < FIREBASE_PASSWORD_STANDART){
            errorType = ERROR_PASSWORD_LENGTH;
        }
        return errorType;
    }

    protected int isValidData(String name,String email,String password) {
        //Validation for Signup
        int errorType = isValidData(email,password);
        if(TextUtils.isEmpty(name)){
            errorType = ERROR_FIELDS_EMPTY;
        }
        return errorType;
    }

    public boolean isNameTaken(String name){
       return false; //TODO if name is already taken
    }
}
