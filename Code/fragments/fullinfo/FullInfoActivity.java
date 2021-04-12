package com.example.bstufinderv2.fragments.fullinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bstufinderv2.MainActivity;
import com.example.bstufinderv2.R;
import com.example.bstufinderv2.async.DeleteItemToSql;
import com.example.bstufinderv2.async.GetUserInfoToSql;
import com.example.bstufinderv2.helpers.Item;
import com.google.android.material.button.MaterialButton;

import java.util.concurrent.ExecutionException;

public class FullInfoActivity extends AppCompatActivity {

    ImageView iv;
    TextView full_info_name_tv, full_info_description_tv, full_info_date_tv, full_info_place_tv,
            full_info_find_info;
    Item item;
    MaterialButton remove_bt;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_info);

        iv = findViewById(R.id.full_info_image);
        full_info_name_tv = findViewById(R.id.full_info_name);
        full_info_description_tv = findViewById(R.id.full_info_description);
        full_info_date_tv = findViewById(R.id.full_info_date);
        full_info_place_tv = findViewById(R.id.full_info_place);
        full_info_find_info = findViewById(R.id.full_info_find_info);
        remove_bt = findViewById(R.id.remove_bt);

        GetData();



        SetData();
        try {
            if (MainActivity.UserInfo.getInstance().id == Integer.parseInt(item.getFounder_id()) || MainActivity.UserInfo.getInstance().role==true)
                remove_bt.setVisibility(View.VISIBLE);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        remove_bt.setOnClickListener(v -> {
            DeleteItemToSql deleteItemToSql = new DeleteItemToSql();
            deleteItemToSql.execute(item);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });
    }

    private void GetData() {
        if (getIntent().hasExtra("detail"))
            position = getIntent().getIntExtra("detail", 1);
    }

    private void SetData() {
        item = MainActivity.itemList.get(position);
        Bitmap bmp = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
        iv.setImageBitmap(bmp);
        full_info_date_tv.setText(item.getDate());
        full_info_description_tv.setText(item.getDescription());
        full_info_name_tv.setText(item.getName());
        full_info_place_tv.setText(item.getPlace());
        GetUserInfoToSql info = new GetUserInfoToSql();
        try {
            String fullinfo = info.execute(item).get();
            full_info_find_info.setText(fullinfo);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}