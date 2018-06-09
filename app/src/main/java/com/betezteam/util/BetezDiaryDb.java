package com.betezteam.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.betezteam.DiaryModel.DiaryPage;

import java.sql.Date;

public class BetezDiaryDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "betez_diary";
    private static final String TABLE_NAME = "diary";
    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String CONTENT = "content";

    private Context context;

    public BetezDiaryDb(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE diary (" +
                "id integer primary key autoincrement," +
                "date text not null unique," +
                "content text)";
        db.execSQL(query);

    }

    public void submmit(String date, String content) {

        try {
            content = textValid(content);

//            Log.d("cg", date);
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "select * from diary where date = '" + date + "'";
            Cursor result = db.rawQuery(query, null);

            ContentValues contentValues = new ContentValues();
            contentValues.put("date", date);
            contentValues.put("content", content);

            if (result.getCount() == 0) {
                db.insert("diary", null, contentValues);
            } else {
                db.update("diary", contentValues, "date = '" + date + "'", null);
            }



        } catch (Exception e) {
            Toast.makeText(this.context, "Có lỗi xảy ra khi Lưu dữ liệu", Toast.LENGTH_LONG).show();
        }

    }

    private static String textValid(String text) {
        text = text.trim();
        text = sqlEscapeString(text);
        return text;
    }

    private static String sqlEscapeString(String text) {
        return text.replace("'", "\'");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public Cursor getPageByDate(String date) {
        String query = "Select * from diary where date = '" + date + "'";
        return this.getReadableDatabase().rawQuery(query, null);
    }

    public Cursor getTop30(){
        String query = "SELECT * FROM diary order by date desc limit 30";
        return this.getReadableDatabase().rawQuery(query, null);
    }

}
