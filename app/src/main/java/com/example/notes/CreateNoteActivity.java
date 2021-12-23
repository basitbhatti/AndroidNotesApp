package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateNoteActivity extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore firestore;

    EditText etTitle, etContent;
    FloatingActionButton save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        etTitle = findViewById(R.id.editTextTitle);
        etContent = findViewById(R.id.editTextContent);
        save = findViewById(R.id.fabSave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();

                Boolean valid = true;
                if (TextUtils.isEmpty(title)) {
                    etTitle.setError("Title cannot be empty");
                    valid = false;
                }

                if (TextUtils.isEmpty(content)) {
                    etContent.setError("Note cannot be empty");
                    valid = false;
                }

                if (valid){
                    ProgressDialog dialog = new ProgressDialog(CreateNoteActivity.this);
                    dialog.setMessage("Adding the note...");
                    dialog.show();
                    DocumentReference documentReference = firestore.collection("notes").document(user.getUid()).collection("myNotes").document();

                    Map<String, Object> map = new HashMap<>();
                    map.put("title", title);
                    map.put("note", content);

                    documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            dialog.dismiss();
                            Toast.makeText(CreateNoteActivity.this, "Note added successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreateNoteActivity.this, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(CreateNoteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });






    }


    public void BackButton(View view) {
        super.onBackPressed();
    }
}