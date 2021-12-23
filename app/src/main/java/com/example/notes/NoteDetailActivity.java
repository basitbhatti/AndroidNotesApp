package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class NoteDetailActivity extends AppCompatActivity {


    TextView tvTitle, tvNote;

    String title, note, docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        getSupportActionBar().hide();


        tvTitle = findViewById(R.id.tvTitle);
        tvNote = findViewById(R.id.tvNote);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        note = intent.getStringExtra("note");
        docId = intent.getStringExtra("docId");


        tvTitle.setText(title);
        tvNote.setText(note);

    }


    public void BackButton(View view) {
        super.onBackPressed();
    }

    public void EditNote(View view) {

        Intent intent = new Intent(NoteDetailActivity.this, EditNoteActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("note", note);
        intent.putExtra("docId", docId);
        startActivity(intent);


    }

}