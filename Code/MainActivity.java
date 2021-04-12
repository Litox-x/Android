package com.example.bstufinderv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bstufinderv2.async.GetItemsToSql;
import com.example.bstufinderv2.async.GetUserItemsToSql;
import com.example.bstufinderv2.async.RegisterUserToSql;
import com.example.bstufinderv2.fragments.sign.SignActivity;
import com.example.bstufinderv2.helpers.ActiveUser;
import com.example.bstufinderv2.fragments.AddFragment;
import com.example.bstufinderv2.fragments.MainFragment;
import com.example.bstufinderv2.fragments.ProfileFragment;
import com.example.bstufinderv2.helpers.DbController;
import com.example.bstufinderv2.helpers.Item;
import com.example.bstufinderv2.helpers.Note;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static ActiveUser UserInfo;
    public static List<Item> itemList;
    public static List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,
                new MainFragment()).commit();
    /*    UserInfo.getInstance(1,"432","432","432",true);
        UserInfo.instance=null;*/
        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(getBottomNavigationListener());
        GetItems();
        noteList = GetNotes();
        if (UserInfo.instance == null) {
            try {
                DesirialUserInfo();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @NonNull
    private BottomNavigationView.OnNavigationItemSelectedListener getBottomNavigationListener() {
        return (item) -> {
            switch (item.getItemId()) {
                case R.id.action_main:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,
                            new MainFragment()).addToBackStack(null).commit();
                    break;
                case R.id.action_add:
                    UserCheck();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,
                            new AddFragment()).addToBackStack(null).commit();

                    break;
                case R.id.action_profile:
                    UserCheck();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,
                            new ProfileFragment()).addToBackStack(null).commit();
                    break;
            }
            return true;
        };


    }

    private void DesirialUserInfo() {
        Gson gson = new GsonBuilder().setLenient().create();
        File f = new File(getFilesDir(), "ActiveUser.json");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            char[] buf = new char[(int) f.length()];
            reader.read(buf);
            reader.close();
            JsonReader jsonReader = new JsonReader(reader);
            String s = new String(buf);
            UserInfo.instance = gson.fromJson(s, ActiveUser.class);
            Toast.makeText(this, "Добро пожаловать" + UserInfo.getInstance().name, Toast.LENGTH_SHORT);
        } catch (IOException e) {
            Log.d("userinfo", e.getMessage());

        }
    }


    private void UserCheck() {
        if (UserInfo.getInstance() == null) {
            Intent intent = new Intent(this, SignActivity.class);
            startActivity(intent);
        }
    }

    public static void GetItems() {
        if (itemList != null)
            itemList.clear();
        GetItemsToSql sql = new GetItemsToSql();
        try {
            MainActivity.itemList = sql.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void GetUserItems() {
        if (itemList != null)
            itemList.clear();
        GetUserItemsToSql sql = new GetUserItemsToSql();
        try {
            MainActivity.itemList = sql.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Note> GetNotes() {
        DbController dbController = new DbController(this);
        ArrayList<Note> notes = new ArrayList<Note>();
        dbController.open();
        Cursor k = dbController.getAllNotes();
        try {
            k.moveToFirst();
            do {
                notes.add(new Note(k.getString(1), k.getString(2)));
            } while (k.moveToNext());

        } catch (Exception e) {
            e.printStackTrace();
        }
        dbController.close();
        return notes;
    }

    @Override
    public void onBackPressed() {
        return;
    }

}
