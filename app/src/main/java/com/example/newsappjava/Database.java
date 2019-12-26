package com.example.newsappjava;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sqllite_database";//database ad�

    private static final String TABLE_NAME = "haberler";
    private static String BASLIK = "title";
    private static String HABER_ID = "id";
    private static String YAZAR = "author";
    private static String ACIKLAMA = "description";
    private static String IMAGE = "urlToImage";
    private static String ICERIK = "content";
    private static String YAYIN_TARIHI = "publishedAt";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + HABER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BASLIK + " TEXT  NOT NULL, "
                + YAZAR + " TEXT  NOT NULL, "
                + ACIKLAMA + " TEXT  NOT NULL, "
                + ICERIK + " TEXT  NOT NULL, "
                + YAYIN_TARIHI + " TEXT  NOT NULL, "
                + IMAGE + " TEXT  NOT NULL " + ")";
        db.execSQL(CREATE_TABLE);
    }


    public void haberEkle(String baslik, String yazar,String aciklama,String icerik,String yayin_tarihi,String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BASLIK, baslik);
        values.put(YAZAR, yazar);
        values.put(ACIKLAMA, aciklama);
        values.put(ICERIK, icerik);
        values.put(YAYIN_TARIHI, yayin_tarihi);
        values.put(IMAGE, image);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public  ArrayList<HashMap<String, String>> haberler(){

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> haberlist = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    String a = cursor.getString(i);
                    map.put(cursor.getColumnName(i), cursor.getString(i));

                }

                haberlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return haberlist;
    }


    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        return rowCount;
    }


    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
