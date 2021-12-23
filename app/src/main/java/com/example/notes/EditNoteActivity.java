package com.example.notes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore firestore;

    EditText etTitle, etContent;
    FloatingActionButton save;

    String title, note, docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        note = intent.getStringExtra("note");
        docId = intent.getStringExtra("docId");
        Toast.makeText(EditNoteActivity.this, docId+"", Toast.LENGTH_SHORT).show();

        etTitle = findViewById(R.id.editTextTitle);
        etContent = findViewById(R.id.editTextContent);
        save = findViewById(R.id.fabSave);

        etTitle.setText(title);
        etContent.setText(note);


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

                if (valid) {
                    ProgressDialog progressDialog = new ProgressDialog(EditNoteActivity.this);
                    progressDialog.setMessage("Updating note...");
                    progressDialog.show();

                    DocumentReference documentReference = firestore.collection("notes").document(user.getUid()).collection("myNotes").document(docId);

                    Map<String, Object> map = new HashMap<>();

                    map.put("title", title);
                    map.put("note", content);

                    documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            startActivity(new Intent(EditNoteActivity.this, MainActivity.class));
                            finish();

                            Toast.makeText(EditNoteActivity.this, "Note Updated Successfully.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditNoteActivity.this, "Could not update the note. "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });

    }



}