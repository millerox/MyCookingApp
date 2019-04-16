package com.example.mycookingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends BasicActivity {

    private Button btnReset;
    private EditText inputEmail;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        btnReset = findViewById(R.id.btn_reset);
        inputEmail = findViewById(R.id.reset_email);
        auth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(v);
            }
        });
    }

    public void resetPassword(View view){
        final String email = inputEmail.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            inputEmail.setError("Please enter valid email");
        }
        else{
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Link to resend your password was sent to " + email, Toast.LENGTH_SHORT).show();
                            inputEmail.setText("");
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Failed to send a link to " + email, Toast.LENGTH_SHORT).show();
                        }
                }
            });
        }

    }

}
