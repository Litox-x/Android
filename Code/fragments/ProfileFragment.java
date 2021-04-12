package com.example.bstufinderv2.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bstufinderv2.MainActivity;
import com.example.bstufinderv2.R;
import com.google.android.material.button.MaterialButton;

import java.io.File;

public class ProfileFragment extends Fragment {

    MaterialButton exit_bt,notes_bt,my_adds_bt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        exit_bt=view.findViewById(R.id.exit_bt);
        my_adds_bt=view.findViewById(R.id.my_adds_bt);
        notes_bt=view.findViewById(R.id.my_notes);
        notes_bt.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,
                    new NotesFragment()).addToBackStack(null).commit();
        });


        my_adds_bt.setOnClickListener(v -> {
            MainActivity.GetUserItems();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,
                    new MainFragment()).addToBackStack(null).commit();
        });
        exit_bt.setOnClickListener(v -> {
            MainActivity.UserInfo.instance=null;
            File f = new File(getActivity().getFilesDir(), "ActiveUser.json");
            f.delete();
            Intent intent = new Intent(getContext(),MainActivity.class);
            startActivity(intent);
        });

        return view;
    }
}