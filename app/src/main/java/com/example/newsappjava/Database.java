package com.example.newsappjava;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "sqllite_database";//database ad�

	private static final String TABLE_NAME = "haberler";
	private static String BASLIK = "baslik";
	private static String HABER_ID = "id";
	private static String YAZAR = "yazar";
	private static String ACIKLAMA = "yil";
	private static String IMAGE = "image";
    private static String ICERIK = "icerik";
    private static String YAYIN_TARIHI = "yayin_tarihi";
	
	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {  // Databesi olu�turuyoruz.Bu methodu biz �a��rm�yoruz. Databese de obje olu�turdu�umuzda otamatik �a��r�l�yor.
		String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ HABER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ BASLIK + " TEXT,"
				+ YAZAR + " TEXT,"
				+ ACIKLAMA + " TEXT,"
                + ICERIK + " TEXT,"
                + YAYIN_TARIHI + " TEXT,"
				+ IMAGE + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);
	}

	public void kitapSil(int id){ //id si belli olan row u silmek i�in
		
		 SQLiteDatabase db = this.getWritableDatabase();
		 db.delete(TABLE_NAME, KITAP_ID + " = ?",
		            new String[] { String.valueOf(id) });
		 db.close();
	}
	
	public void haberEkle(String baslik, String yazar,String aciklama,String icerik,String yayin_tarihi,String image) { 
		//kitapEkle methodu ise ad� �st�nde Databese veri eklemek i�in 
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BASLIK, baslik);
		values.put(YAZAR, yazar);
		values.put(ACIKLAMA, aciklama);
		values.put(ICERIK, icerik);
        values.put(YAYIN_TARIHI, yayin_tarihi);
        values.put(IMAGE, image);

		db.insert(TABLE_NAME, null, values);
		db.close(); //Database Ba�lant�s�n� kapatt�k*/
	}
	
	public  ArrayList<HashMap<String, String>> haberler(){
		
		//Bu methodda ise tablodaki t�m de�erleri al�yoruz
		//ArrayList ad� �st�nde Array lerin listelendi�i bir Array.Burda hashmapleri listeleyece�iz
		//Herbir sat�r� de�er ve value ile hashmap a at�yoruz. Her bir sat�r 1 tane hashmap array� demek.
		//olusturdugumuz t�m hashmapleri ArrayList e at�p geri d�n�yoruz(return).
		
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		Cursor cursor = db.rawQuery(selectQuery, null);
	    ArrayList<HashMap<String, String>> kitaplist = new ArrayList<HashMap<String, String>>();
	    
	    if (cursor.moveToFirst()) {
	        do {
	            HashMap<String, String> map = new HashMap<String, String>();
	            for(int i=0; i<cursor.getColumnCount();i++)
	            {
	                map.put(cursor.getColumnName(i), cursor.getString(i));
	            }
	
	            kitaplist.add(map);
	        } while (cursor.moveToNext());
	    }
	    db.close();
	    // return kitap liste
	    return kitaplist;
	}
	
	
	public int getRowCount() { 
		// Bu method bu uygulamada kullan�lm�yor ama her zaman laz�m olabilir.Tablodaki row say�s�n� geri d�ner.
		//Login uygulamas�nda kullanaca��z
		String countQuery = "SELECT  * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		// return row count
		return rowCount;
	}
	

	public void resetTables(){ 
		//Bunuda uygulamada kullanm�yoruz. T�m verileri siler. tabloyu resetler.
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
