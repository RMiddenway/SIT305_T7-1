package com.rodnog.rogermiddenway.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.NOTE_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Util.NOTE + " TEXT);";
        db.execSQL(CREATE_NOTE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_NOTE_TABLE = "DROP TABLE IF EXISTS ";
        db.execSQL(DROP_NOTE_TABLE + Util.TABLE_NAME);
        onCreate(db);

    }

    public long insertNote(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NOTE, text);
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }
    public long updateNote(int id, String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NOTE, text);
        contentValues.put(Util.NOTE_ID, id);

        long rowId = db.update(Util.TABLE_NAME, contentValues, Util.NOTE_ID + " = ?", new String[]{String.valueOf(id)});

        db.close();
        return rowId;
    }

    public void deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ Util.TABLE_NAME + " where " + Util.NOTE_ID + "=" + String.valueOf(id));
        db.close();

    }

    public Note fetchNote(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.NOTE_ID + "=" + String.valueOf(id) + ";";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            db.close();
            Note note = new Note();
            note.setId(cursor.getInt(cursor.getColumnIndex(Util.NOTE_ID)));
            note.setText(cursor.getString(cursor.getColumnIndex(Util.NOTE)));

            return note;
        }
        else{
            db.close();
            return new Note();
        }
    }

    public List<Note> fetchAllNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAll = "SELECT *  FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);
        List<Note> noteList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setText(cursor.getString(cursor.getColumnIndex((Util.NOTE))));
                note.setId(cursor.getInt(cursor.getColumnIndex(Util.NOTE_ID)));
                noteList.add(note);
            }
            while(cursor.moveToNext());
            db.close();
        }

        return noteList;
    }
}
