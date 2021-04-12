package com.example.bstufinderv2.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bstufinderv2.R;


public class CorpsFragment extends Fragment {

    TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_corps, container, false);
        tv = view.findViewById(R.id.tv_corps);
        tv.setText(AddFragment.corps);
        return view;
    }
}