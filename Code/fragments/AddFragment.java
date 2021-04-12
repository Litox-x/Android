package com.example.bstufinderv2.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bstufinderv2.MainActivity;
import com.example.bstufinderv2.R;
import com.example.bstufinderv2.async.InsertToSql;
import com.example.bstufinderv2.helpers.Item;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class AddFragment extends Fragment {

    private final int Pick_image = 1;
    EditText name, description;
    MaterialButton bt_add_photo, bt_add_info, bt_submit;
    ShapeableImageView imageView;
    byte[] image;
    public static String corps = "Корпус не выбран";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        name = view.findViewById(R.id.add_name);
        description = view.findViewById(R.id.add_description);
        bt_add_photo = view.findViewById(R.id.add_photo_bt);
        bt_add_info = view.findViewById(R.id.add_info_bt);
        bt_submit = view.findViewById(R.id.add_submit_bt);
        imageView = view.findViewById(R.id.add_image);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.info_pick,
                new CorpsFragment()).commit();

        bt_add_photo.setOnClickListener(v -> {

            //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            //Тип получаемых объектов - image:
            photoPickerIntent.setType("image/*");
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(photoPickerIntent, Pick_image);

        });
        bt_add_info.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.info_pick,
                    new InfoPickFragment()).commit();
        });
        bt_submit.setOnClickListener(v -> {
            if(name.getText().toString()=="" || image==null){
                Toast.makeText(getContext(),"Загрузите фото или заполните графу название",Toast.LENGTH_SHORT).show();
                return;
            }
            Item item = new Item("2", name.getText().toString(), description.getText().toString(), image, corps, new Date().toString(), Integer.toString(MainActivity.UserInfo.getInstance().id));
            InsertToSql insert = new InsertToSql();
            insert.execute(item);
            Toast.makeText(getContext(),"Успешно добавлено",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(),MainActivity.class);
            startActivity(intent);
            AddFragment.corps = "Корпус не выбран";
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case Pick_image:
                if (resultCode == getActivity().RESULT_OK) {
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                        image = stream.toByteArray();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
        }
    }
}