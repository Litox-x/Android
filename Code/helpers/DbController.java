package com.example.bstufinderv2.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbController {
    public static final String TABLE_NOTES = "NOTES";
    public static final String ID = "_id";
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";


    private Context context;

    private SQLiteHelper sqLiteHelper;
    public SQLiteDatabase db;

    public DbController(Context context) {
        this.context = context;
    }

    public void open() {
        sqLiteHelper = new SQLiteHelper(context);
        db = sqLiteHelper.getWritableDatabase();
    }
    public void add(ContentValues cv){
        db.insert(TABLE_NOTES,null,cv);
    }

    public void remove(Note note){
        db.delete(TABLE_NOTES,"DESCRIPTION = ?",new String[]{note.getDesctiption()});
    }
    public void update(ContentValues cv,Note note){
        db.update(TABLE_NOTES,cv,"DESCRIPTION = ?",new String[]{note.getDesctiption()});
    }
    public void close() {
        if(sqLiteHelper != null) sqLiteHelper.close();
    }

    public Cursor getAllNotes() {
        return db.rawQuery("select * from " + TABLE_NOTES, null);
    }

}
