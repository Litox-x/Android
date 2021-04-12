package com.example.bstufinderv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bstufinderv2.helpers.DbController;
import com.example.bstufinderv2.helpers.Note;
import com.google.android.material.button.MaterialButton;

public class AddNoteActivity extends AppCompatActivity {

    MaterialButton add_bt;
    EditText title, description;
    int position;
    Note item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title = findViewById(R.id.add_title_note);
        description = findViewById(R.id.add_description_note);
        add_bt = findViewById(R.id.add_note_to_db_bt);
        if (getIntent().hasExtra("detail")) {
            GetData();
            SetData();
            add_bt.setOnClickListener(v -> {
                ContentValues cv = new ContentValues();
                cv.put("TITLE", title.getText().toString());
                cv.put("DESCRIPTION", description.getText().toString());
                DbController dbController = new DbController(this);
                dbController.open();
                dbController.update(cv, item);
                dbController.close();
                Toast.makeText(this, "Заметка обновлена", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
        } else
            add_bt.setOnClickListener(v -> {
                ContentValues cv = new ContentValues();
                cv.put("TITLE", title.getText().toString());
                cv.put("DESCRIPTION", description.getText().toString());
                DbController dbController = new DbController(this);
                dbController.open();
                dbController.add(cv);
                dbController.close();
                Toast.makeText(this, "Заметка добавлена", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
    }

    private void GetData() {
        if (getIntent().hasExtra("detail"))
            position = getIntent().getIntExtra("detail", 1);
    }

    private void SetData() {
        item = MainActivity.noteList.get(position);
        title.setText(item.getTitle());
        description.setText(item.getDesctiption());
    }
}