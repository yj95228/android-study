package com.scsa.workshop7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper { // SQLiteOpenHelper : table create, table alter table.

    private static final String TAG = "DBHelper_SCSA";

    private static final String TABLE_NAME = "mytable";
    private static final String[] COLUMNS = {"_id", "title", "body", "reg_date"};
    private SQLiteDatabase db;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // {"_id", "title", "body", "reg_date"};
    // 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + "\n");
        sql.append(COLUMNS[0] + " integer primary key autoincrement, \n");
        sql.append(COLUMNS[1] + " text, \n");
        sql.append(COLUMNS[2] + " text, \n");
        sql.append(COLUMNS[3] + " integer \n");
        sql.append(");");

        db.execSQL(sql.toString());

        Log.d(TAG, "onCreate()::테이블 생성 완료");
    }

    // 업그레이드: 기존 테이블 DROP하고 새롭게 생성
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);

        Log.d(TAG, "onUpgrade()::업그레이드 완료");
    }

    public void open(){
        db = getWritableDatabase();
        Log.d(TAG, "onOpen()::Database 준비 완료");
    }

    //    // {"_id", "title", "body", "reg_date"};
    public void insert(MemoDto dto) {
        ContentValues values = new ContentValues(); // key - value
        values.put(COLUMNS[1], dto.getTitle());
        values.put(COLUMNS[2], dto.getBody());
        values.put(COLUMNS[3], System.currentTimeMillis());

        db.beginTransaction();

        // 1. 메서드를 이용한 INSERT
        long result = db.insert(TABLE_NAME, null, values);

        // 2. SQL을 이용한 INSERT
//        StringBuilder sql = new StringBuilder();
//        sql.append("INSERT INTO " + TABLE_NAME + " (" + COLUMNS[1] + ") \n");
//        sql.append("VALUES('" + message + "') \n");
//        db.execSQL(sql.toString());

        if (result > 0) {
            db.setTransactionSuccessful();
        }

        db.endTransaction();
    }

    public MemoDto select(String id) {
        Cursor cursor = db.query(TABLE_NAME, COLUMNS,
                "_id = ?", new String[]{id},
                null, null, null);

//        cursor = db.rawQuery("select * from mytable where _id = ?", new String[]{id});

        MemoDto dto = null;
//        StringBuilder result = new StringBuilder();
//        result.append(COLUMNS[0] + ", " + COLUMNS[1] + "\n");
        if (cursor.moveToNext()) {
            dto = new MemoDto(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3) );
//            result.append(cursor.getInt(0) + ", " + cursor.getString(1) + "\n");
        }

        return dto;
    }

    public List<MemoDto> selectAll() {
        // 1. 메서드를 이용한 SELECT
//        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);

        // 2. SQL을 이용한 SELECT
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        List<MemoDto> list = new ArrayList<>();
        while(cursor.moveToNext()){
            list.add(new MemoDto(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getInt(3) ));
        }

        return list;
    }
    // _id, title, body, reg_date
    public void update(MemoDto dto) {
        ContentValues values = new ContentValues();
        values.put(COLUMNS[1], dto.getTitle());
        values.put(COLUMNS[2], dto.getBody());

        db.beginTransaction();

        // 1. 메서드를 이용한 UPDATE
        int result = db.update(TABLE_NAME, values,
                "_id = ?", new String[]{ dto.getId() + "" });

        // 2. SQL을 이용한 UPDATE
//        StringBuilder sql = new StringBuilder();
//        sql.append("UPDATE " + TABLE_NAME + " \n");
//        sql.append("SET message = " + message + " \n");
//        sql.append("WHERE _id = " + id + " \n");
//        db.execSQL(sql.toString());

        if (result > 0) {
            db.setTransactionSuccessful();
        }

        db.endTransaction();
    }

    public void delete(String id) {
        db.beginTransaction();

        // 1. 메서드를 이용한 DELETE
        int result = db.delete(TABLE_NAME,
                "_id = ?", new String[]{id});

        // 2. SQL을 이용한 UPDATE
//        StringBuilder sql = new StringBuilder();
//        sql.append("DELETE \n");
//        sql.append("FROM " + TABLE_NAME + " \n");
//        sql.append("WHERE _id = " + id + " \n");
//        db.execSQL(sql.toString());

        if (result > 0) {
            db.setTransactionSuccessful();
        }

        db.endTransaction();
    }
}
