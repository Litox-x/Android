package com.example.bstufinderv2.fragments.sign;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bstufinderv2.MainActivity;
import com.example.bstufinderv2.R;
import com.example.bstufinderv2.async.GetUserToSql;
import com.example.bstufinderv2.helpers.ActiveUser;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class SignInFragment extends Fragment {


    EditText et_pass, et_email;
    MaterialButton signin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        et_pass = view.findViewById(R.id.sign_in_pass);
        et_email = view.findViewById(R.id.sign_in_email);
        signin = view.findViewById(R.id.sign_in_bt);

        signin.setOnClickListener(v -> {
            GetUserToSql sql = new GetUserToSql();

            try {
                ResultSet result = sql.execute(et_email.getText().toString(), et_pass.getText().toString()).get();
                if (result == null)
                    Toast.makeText(getContext(), "Неверное имя пользователя или пароль", Toast.LENGTH_SHORT).show();
                else {
                    result.next();
                    Toast.makeText(getContext(), "Добро пожаловать,"+result.getString(2), Toast.LENGTH_SHORT).show();
                    ActiveUser.getInstance(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),result.getBoolean(6));
                    SerializationInfo();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Неверное имя пользователя или пароль", Toast.LENGTH_SHORT).show();

            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Неверное имя пользователя или пароль", Toast.LENGTH_SHORT).show();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                Toast.makeText(getContext(), "Неверное имя пользователя или пароль", Toast.LENGTH_SHORT).show();

            }


        });
        return view;
    }
    private void SerializationInfo(){
        Gson gson = new Gson();
        String jser = gson.toJson(MainActivity.UserInfo.getInstance());
        File f = new File(getActivity().getFilesDir(), "ActiveUser.json");
        try{
            FileWriter w = new FileWriter(f);
            w.write(jser);
            w.close();
            Toast.makeText(getContext(),"Успешно сериализованно",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e){
            Log.d("userinf", e.getMessage());
        }
    }
}