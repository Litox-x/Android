package com.example.bstufinderv2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bstufinderv2.R;

public class InfoPickFragment extends Fragment {
    Button button1, button2, button3, button4, button_kitchen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_pick, container, false);

        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button_kitchen = view.findViewById(R.id.button_kitchen);
        button1.setOnClickListener(v -> {
            AddFragment.corps = "Корпус 1";
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.info_pick,
                    new CorpsFragment()).commit();
        });
        button2.setOnClickListener(v -> {
            AddFragment.corps = "Корпус 2";
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.info_pick,
                    new CorpsFragment()).commit();
        });
        button3.setOnClickListener(v -> {
            AddFragment.corps = "Корпус 3/3а";
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.info_pick,
                    new CorpsFragment()).commit();
        });
        button4.setOnClickListener(v -> {
            AddFragment.corps = "Корпус 4/4а";
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.info_pick,
                    new CorpsFragment()).commit();
        });
        button_kitchen.setOnClickListener(v -> {
            AddFragment.corps = "Cтоловая";
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.info_pick,
                    new CorpsFragment()).commit();
        });

        return view;
    }
}