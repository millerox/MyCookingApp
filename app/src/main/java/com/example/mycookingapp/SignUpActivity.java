package com.example.mycookingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText user_email;
    private EditText user_name;
    private EditText user_psw;
    private FirebaseAuth user_auth;
    private FirebaseUser current_user;

    DatabaseReference firebaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        user_email = findViewById(R.id.signup_email);
        user_name = findViewById(R.id.signup_name);
        user_psw = findViewById(R.id.signup_password);
        user_auth = FirebaseAuth.getInstance();

        firebaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    public void createFirebaseUser(View view){
        String name = user_name.getText().toString();
        String email = user_email.getText().toString().trim();
        String password = user_psw.getText().toString().trim();

        user_auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(SignUpActivity.this,"createUserWithEmail:onComplete:" + task.isSuccessful(),
                        Toast.LENGTH_SHORT).show();

                if (!task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    sendVerification();
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                    finish();
                }
            }
        });
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
        String name = user_name.getText().toString();
        String email = user_email.getText().toString();
        String password = user_psw.getText().toString();
        boolean verified = false;

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) )
        {
            String id = firebaseReference.push().getKey();
            User user = new User(id,name,password,email,verified);
            firebaseReference.child(id).setValue(user);
            //Clear the input fields
            user_name.setText("");
            user_email.setText("");
            user_psw.setText("");
        }
        else
        {
            Toast.makeText(SignUpActivity.this,"Field(s) are empty",Toast.LENGTH_LONG).show();
        }
    }
}
