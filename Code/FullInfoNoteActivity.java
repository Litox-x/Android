package com.example.bstufinderv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bstufinderv2.async.GetUserInfoToSql;
import com.example.bstufinderv2.helpers.DbController;
import com.example.bstufinderv2.helpers.Item;
import com.example.bstufinderv2.helpers.Note;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.ExecutionException;

public class FullInfoNoteActivity extends AppCompatActivity {

    TextView full_info_title_note,full_info_description_note;
    MaterialButton edit_note_bt, remove_note_bt;
    Note item;
    int position;
    DbController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_info_note);
        full_info_title_note = findViewById(R.id.full_info_title_note);
        full_info_description_note = findViewById(R.id.full_info_description_note);
        edit_note_bt = findViewById(R.id.edit_note_bt);
        remove_note_bt = findViewById(R.id.remove_note_bt);
        dbController = new DbController(this);
        GetData();
        SetData();

        edit_note_bt.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddNoteActivity.class);
            intent.putExtra("detail", position);
            startActivity(intent);
        });

        remove_note_bt.setOnClickListener(v -> {
            dbController.open();
            dbController.remove(item);
            dbController.close();
            Toast.makeText(this,"Успешно удалено",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });
    }

    private void GetData() {
        if (getIntent().hasExtra("detail"))
            position = getIntent().getIntExtra("detail", 1);
    }

    private void SetData() {
        item = MainActivity.noteList.get(position);
        full_info_title_note.setText(item.getTitle());
        full_info_description_note.setText(item.getDesctiption());
    }
}