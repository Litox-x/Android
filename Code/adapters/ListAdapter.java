package com.example.bstufinderv2.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bstufinderv2.R;
import com.example.bstufinderv2.fragments.fullinfo.FullInfoActivity;
import com.example.bstufinderv2.helpers.Item;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    List<Item> itemList;

    public ListAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_name.setText(itemList.get(position).getName());
        holder.tv_description.setText(itemList.get(position).getDescription());
        Bitmap bmp = BitmapFactory.decodeByteArray(itemList.get(position).getImage(),0,itemList.get(position).getImage().length);
        holder.imageView.setImageBitmap(bmp);
        holder.tv_post_date.setText(itemList.get(position).getDate());

        holder.mainLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullInfoActivity.class);
            intent.putExtra("detail", position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView tv_name, tv_description,tv_post_date;
        ImageView imageView;
        CardView mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.item_name);
            tv_description = itemView.findViewById(R.id.item_descripion);
            tv_post_date = itemView.findViewById(R.id.item_post_date);
            imageView = itemView.findViewById(R.id.item_iv);
            mainLayout = itemView.findViewById(R.id.main_layout);

        }



        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
}
