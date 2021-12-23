package com.example.notes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "FIREBASE-AUTH";

    private FirebaseAuth mAuth;
    Button btnSignUp;
    EditText editTextEmail, editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        editTextEmail = findViewById(R.id.editEmail);
        editTextPassword = findViewById(R.id.editPassword);
        btnSignUp = findViewById(R.id.btnSignup);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
                progressDialog.setMessage("Creating Account!");

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                Boolean valid = true;
                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Email address cannot be empty");
                    valid = false;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("last name cannot be empty");
                    valid = false;
                }

                if (valid) {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                sendEmailNotification();
                            }

                        }

                        private void sendEmailNotification() {

                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null){
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(SignupActivity.this, "Verification email has been sent to your email.", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    }
                                });

                            }

                        }
                    });
                }


            }
        });

    }

    public void SignInInstead(View view) {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }
}