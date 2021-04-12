package com.example.bstufinderv2.fragments.sign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bstufinderv2.R;
import com.example.bstufinderv2.async.RegisterUserToSql;
import com.example.bstufinderv2.fragments.ProfileFragment;
import com.google.android.material.button.MaterialButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    EditText et_pass, et_pass_check, et_name, et_email, et_phone;
    MaterialButton reg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        et_pass = view.findViewById(R.id.sign_up_pass);
        et_pass_check = view.findViewById(R.id.sign_up_pass_check);
        et_name = view.findViewById(R.id.sign_up_name);
        et_email = view.findViewById(R.id.sign_up_email);
        et_phone = view.findViewById(R.id.sign_up_phone);
        reg = view.findViewById(R.id.sign_up_bt);


        reg.setOnClickListener(v -> {
            RefreshColor();
            String pass = et_pass.getText().toString();
            String pass_check = et_pass_check.getText().toString();
            String name = et_name.getText().toString();
            String email = et_email.getText().toString();
            String phone = et_phone.getText().toString();

            String patternemail = "^[a-zA-Z0-9]{0,15}[@][a-zA-Z0-9]{0,15}[.][a-zA-Z]{0,5}$";
            String patternpassword1 = "^[a-zA-Z0-9]{6,20}$";
            String patternpassword2 = "^[a-zA-Z0-9]{6,20}$";
            String patternphone = "^(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})$";

            Pattern patt1 = Pattern.compile(patternemail);
            Pattern patt3 = Pattern.compile(patternpassword1);
            Pattern patt4 = Pattern.compile(patternpassword2);
            Pattern patt5 = Pattern.compile(patternphone);

            Matcher match1 = patt1.matcher(email);
            Matcher match3 = patt3.matcher(pass);
            Matcher match4 = patt3.matcher(pass_check);
            Matcher match5 = patt5.matcher(phone);

            if (name.length() < 2) {
                Toast.makeText(getContext(), "Длинна имени не может быть меньше 2-х символов", Toast.LENGTH_LONG).show();
                et_name.setTextColor(Color.rgb(255, 0, 0));
            } else if (!match1.matches()) {
                Toast.makeText(getContext(), "Неверный формат электронной почты", Toast.LENGTH_LONG).show();
                et_email.setTextColor(Color.rgb(255, 0, 0));
            } else if (!match5.matches()) {
                Toast.makeText(getContext(), "Неверный формат мобильного номера:\n +375339999999\n 80291234567", Toast.LENGTH_LONG).show();
                et_phone.setTextColor(Color.rgb(255, 0, 0));
            } else if (!match3.matches()) {
                Toast.makeText(getContext(), "Неверный формат пароля", Toast.LENGTH_LONG).show();
                et_pass.setTextColor(Color.rgb(255, 0, 0));
            } else if (!pass.equals(pass_check)) {
                Toast.makeText(getContext(), "Неверный повторный пароль", Toast.LENGTH_LONG).show();
                et_pass_check.setTextColor(Color.rgb(255, 0, 0));
            } else {
                Toast.makeText(getContext(), "Регистрация пройдена успешно!", Toast.LENGTH_LONG).show();

                try {
                    RegisterUserToSql k = new RegisterUserToSql();
                    k.execute(name, email, phone, pass);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(getContext(), SignActivity.class);
                startActivity(intent);
            }

        });

        return view;
    }

    void RefreshColor() {
        et_email.setTextColor(Color.rgb(0, 0, 0));
        et_name.setTextColor(Color.rgb(0, 0, 0));
        et_pass_check.setTextColor(Color.rgb(0, 0, 0));
        et_pass.setTextColor(Color.rgb(0, 0, 0));
        et_phone.setTextColor(Color.rgb(0, 0, 0));
    }


}