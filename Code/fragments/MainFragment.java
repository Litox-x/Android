package com.example.bstufinderv2.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bstufinderv2.MainActivity;
import com.example.bstufinderv2.R;
import com.example.bstufinderv2.adapters.ListAdapter;
import com.example.bstufinderv2.async.GetItemsToSql;
import com.example.bstufinderv2.helpers.Item;
import com.example.bstufinderv2.helpers.SQLiteHelper;
import com.google.android.material.button.MaterialButton;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainFragment extends Fragment {

    RecyclerView rv;
    MaterialButton search_bt, refresh_bt;
    EditText search_et;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        search_bt = view.findViewById(R.id.main_search_bt);
        search_et = view.findViewById(R.id.main_search_et);
        refresh_bt = view.findViewById(R.id.main_refresh_bt);
        rv = view.findViewById(R.id.recycle_v);
        ListAdapter listAdapter = new com.example.bstufinderv2.adapters.ListAdapter(getContext(), MainActivity.itemList);
        rv.setAdapter(listAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        if (MainActivity.itemList.size() == 0)
            Toast.makeText(getContext(), "На данный момент список пуст", Toast.LENGTH_SHORT).show();

        refresh_bt.setOnClickListener(v -> {
            MainActivity.GetItems();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,
                    new MainFragment()).commit();
        });

        search_bt.setOnClickListener(v -> {
            List<Item> result = (ArrayList) MainActivity.itemList;
            Pattern pattern = Pattern.compile(search_et.getText().toString());
            List<Item> check = new ArrayList<>();
            for (Item k : result) {
                Matcher matcher = pattern.matcher(k.getName());
                if (matcher.find()) {
                    check.add(k);
                }
            }
            if (check.size() == 0) {
                Toast.makeText(getContext(), "Совпадений не найдено", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Найдено - " + Integer.toString(check.size()) + "совпадене(ий)", Toast.LENGTH_SHORT).show();
                MainActivity.itemList = check;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,
                        new MainFragment()).addToBackStack(null).commit();
            }
        });
        return view;
    }
}