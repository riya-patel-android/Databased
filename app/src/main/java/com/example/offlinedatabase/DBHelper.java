package com.example.offlinedatabase;

import  android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context,"demo",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String qry = "create table contactbook (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,contact TEXT)";
            sqLiteDatabase.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    public void insertData(String name, String contact) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String qry = "insert into contactbook (name,contact) values('"+name+"','"+contact+"')";
        sqLiteDatabase.execSQL(qry);
    }

    public Cursor getData() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String qry = "select * from contactbook";
        Cursor cursor = sqLiteDatabase.rawQuery(qry,null);
        return cursor;
    }

    public void deleteData(int id) {
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        String qry = "delete from contactbook where id = '"+id+"'";
        sqLiteDatabase.execSQL(qry);
    }

    public void updateData(int id, String name, String contact) {
        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        String qry = "update contactbook set name='"+name+"',contact='"+contact+"' where id = '"+id+"'";
        sqLiteDatabase.execSQL(qry);
    }
}
