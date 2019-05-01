package com.example.mycookingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycookingapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends BasicActivity {

    private EditText et_userName;
    private EditText et_userEmail;
    private EditText et_userPsw;

    private FirebaseAuth user_auth;
    private FirebaseUser current_user;

    DatabaseReference firebaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Initializing views:
        et_userName = findViewById(R.id.et_signup_userName);
        et_userEmail = findViewById(R.id.et_signup_userEmail);
        et_userPsw = findViewById(R.id.et_signup_userPsw);
        user_auth = FirebaseAuth.getInstance();
        //Initializing variables:
        firebaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    public void onClick_signup(View view) {
        Intent intent;
        String email = et_userEmail.getText().toString().trim();
        String password = et_userPsw.getText().toString().trim();
        switch (view.getId()){
            case R.id.btn_signup_signup:

                break;
            case R.id.btn_signup_google:
                break;
            case R.id.btn_signup_facebook:
                break;
        }
    }


    public void createFirebaseUser(View view){
        String name = et_userName.getText().toString();
        String email = et_userEmail.getText().toString().trim();
        String password = et_userPsw.getText().toString().trim();

        //Validate inputs and create a user
        if(validateInputs(name,email,password)){
            user_auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            sendVerification();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }
                        }
                    });
        }
    }

    public boolean validateInputs(String name, String email, String password){
        boolean validated = true;
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(SignUpActivity.this, "Name, email or password CAN'T BE EMPTY", Toast.LENGTH_LONG);
            validated = false;
        }
        if(password.length() < 6){
            et_userPsw.setError("Password can't be less than 6 characters");
            validated = false;
        }
        return validated;
    }

    public void sendVerification(){
        current_user = user_auth.getCurrentUser();
        current_user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SignUpActivity.this, "Verification code send to " + current_user.getEmail()+ ". Confirm your email and login",
                        Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Verification FAILED to sent to " + current_user.getEmail(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveUserToDatabase(View view) // To Database
    {
        String name = et_userName.getText().toString();
        String email = et_userEmail.getText().toString();
        String password = et_userPsw.getText().toString();
        boolean verified = false;

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) )
        {
            String id = firebaseReference.push().getKey();
            User user = new User(email,password);
            firebaseReference.child(id).setValue(user);
            //Clear the input fields
            et_userName.setText("");
            et_userEmail.setText("");
            et_userPsw.setText("");
        }
        else
        {
            Toast.makeText(SignUpActivity.this,"Field(s) are empty",Toast.LENGTH_LONG).show();
        }
    }

}
