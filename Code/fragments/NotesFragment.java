package com.example.bstufinderv2.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bstufinderv2.AddNoteActivity;
import com.example.bstufinderv2.MainActivity;
import com.example.bstufinderv2.R;
import com.example.bstufinderv2.adapters.ListAdapter;
import com.example.bstufinderv2.adapters.NotesAdapter;
import com.example.bstufinderv2.helpers.DbController;
import com.example.bstufinderv2.helpers.Item;
import com.example.bstufinderv2.helpers.Note;
import com.example.bstufinderv2.helpers.SQLiteHelper;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class NotesFragment extends Fragment {

    DbController dbController = new DbController(getContext());
    RecyclerView rv;
    MaterialButton add_note_bt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        rv = view.findViewById(R.id.note_rv);
        add_note_bt= view.findViewById(R.id.add_note_bt);
        NotesAdapter listAdapter = new com.example.bstufinderv2.adapters.NotesAdapter(getContext(), MainActivity.noteList);
        rv.setAdapter(listAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        add_note_bt.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddNoteActivity.class);
            startActivity(intent);
        });
        return view;
    }


    public ArrayList<Note> GetItems() {
        ArrayList<Note> notes = new ArrayList<Note>();
        dbController.open();
        Cursor k = dbController.getAllNotes();
        try {
            k.moveToFirst();
            do {
                notes.add(new Note(k.getString(0), k.getString(1)));
            } while (k.moveToNext());

        } catch (Exception e) {
            e.printStackTrace();
        }
        dbController.close();
        return notes;
    }


}