package com.example.bstufinderv2.helpers;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class ActiveUser {
    public static ActiveUser instance;
    public int id;
    public String name;
    public String email;
    public String phone_number;
    public boolean role;

    private ActiveUser(int id,String name, String email, String phone_number, boolean role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.role = role;
    }
    public static ActiveUser getInstance(int id,String name, String email, String phone_number, boolean role){
        if(instance == null){
            instance = new ActiveUser(id,name,email,phone_number,role);
        }
        return instance;
    }
    public static ActiveUser getInstance(){
        if(instance == null){
            return null;
        }
        return instance;
    }
}
