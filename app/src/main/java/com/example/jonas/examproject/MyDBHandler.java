package com.example.jonas.examproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "noteDB.db";
    private static final String TABLE_NOTES = "notes";

    public static final String COLUMN_TITLE = "noteTitle";
    public static final String COLUMN_CONTENT = "noteContext";

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                    TABLE_NOTES + "("
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_CONTENT + " TEXT" + ")";
            db.execSQL(CREATE_PRODUCTS_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
            onCreate(db);

        }
    //Saves new notes
        public void addNote(NoteObject noteObject) {

            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, noteObject.getTitle());
            values.put(COLUMN_CONTENT, noteObject.getContent());

            SQLiteDatabase db = this.getWritableDatabase();

            db.insert(TABLE_NOTES, null, values);
            db.close();
        }

    //Read all notes from DB!
        public ArrayList<NoteObject> getAll() {
            ArrayList<NoteObject> list = new ArrayList<NoteObject>();
            String query = "Select * FROM " + TABLE_NOTES;

            SQLiteDatabase db = this.getWritableDatabase();
            try{
                Cursor cursor = db.rawQuery(query, null);
                try{
                    if (cursor.moveToFirst()) {
                    do {
                        NoteObject noteObject = new NoteObject();
                        noteObject.setTitle(cursor.getString(0));
                        noteObject.setContent(cursor.getString(1));

                        //Adds all objects to list
                        list.add(noteObject);

                    } while (cursor.moveToNext());
                }
            } finally {
                    try { cursor.close(); } catch (Exception ignore) {}
                }
            }finally {
                try { db.close(); } catch (Exception ignore) {}
            }
            return list;
        }

    //Delete note (Not finish)
        public boolean deleteNote(String noteTitle) {

            boolean result = false;

            String query = "Select * FROM " + TABLE_NOTES + " WHERE " + COLUMN_TITLE + " =  \"" + noteTitle + "\"";

            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery(query, null);

            NoteObject noteObject = new NoteObject();

            if (cursor.moveToFirst()) {
                noteObject.setTitle(cursor.getString(0));
                db.delete(TABLE_NOTES, COLUMN_TITLE + " = ?",
                        new String[] { String.valueOf(noteObject.getTitle()) });
                cursor.close();
                result = true;
            }
            db.close();
            return result;
        }

    public void updateNote(String oldTitle, NoteObject noteObject) {

        Log.d("Old title: ", ""+oldTitle);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, noteObject.getTitle());
        values.put(COLUMN_CONTENT, noteObject.getContent());

        // updating row
        db.update(TABLE_NOTES, values, COLUMN_TITLE + " = ?",
                new String[] { String.valueOf(oldTitle) });
    }
}
