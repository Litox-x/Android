package com.example.bstufinderv2.adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bstufinderv2.FullInfoNoteActivity;
import com.example.bstufinderv2.R;
import com.example.bstufinderv2.fragments.fullinfo.FullInfoActivity;
import com.example.bstufinderv2.helpers.Item;
import com.example.bstufinderv2.helpers.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> implements View.OnCreateContextMenuListener {

    Context context;
    List<Note> itemList;

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }




    public NotesAdapter(Context context, List<Note> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_title.setText(itemList.get(position).getTitle());
        holder.tv_description.setText(itemList.get(position).getDesctiption());

        holder.mainLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullInfoNoteActivity.class);
            intent.putExtra("detail", position);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_description;
        CardView mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.note_title);
            tv_description = itemView.findViewById(R.id.note_desc);
            mainLayout = itemView.findViewById(R.id.note_cv);
        }




    }
}
