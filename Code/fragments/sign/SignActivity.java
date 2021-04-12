package com.example.bstufinderv2.fragments.sign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bstufinderv2.MainActivity;
import com.example.bstufinderv2.R;
import com.example.bstufinderv2.fragments.AddFragment;
import com.example.bstufinderv2.fragments.MainFragment;
import com.example.bstufinderv2.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        getSupportFragmentManager().beginTransaction().replace(R.id.sign_mainframe,
                new SignInFragment()).commit();
        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(getBottomNavigationListener());
    }
    @NonNull
    public BottomNavigationView.OnNavigationItemSelectedListener getBottomNavigationListener() {
        return (item) -> {
            switch (item.getItemId()) {
                case R.id.action_signin:
                    getSupportFragmentManager().beginTransaction().replace(R.id.sign_mainframe,
                            new SignInFragment()).addToBackStack(null).commit();
                    break;
                case R.id.action_signup:
                    getSupportFragmentManager().beginTransaction().replace(R.id.sign_mainframe,
                            new SignUpFragment()).addToBackStack(null).commit();
                    break;
            }
            return true;
        };


    }
    @NonNull
    public BottomNavigationView.OnNavigationItemSelectedListener getBottomNavigationListener(int id) {
        return (item) -> {
            if(item.getItemId()==id){
                getSupportFragmentManager().beginTransaction().replace(R.id.sign_mainframe,
                        new SignInFragment()).addToBackStack(null).commit();
            }
            return true;
        };


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}