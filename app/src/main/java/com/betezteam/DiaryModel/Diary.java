package com.betezteam.DiaryModel;

import android.content.Context;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.util.Log;

import com.betezteam.betezdiary.MainActivity;
import com.betezteam.util.BetezDiaryDb;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Diary extends ArrayList<DiaryPage> {
    public Diary() {
        super();
    }

    private void get20data() {

    }

    public static DiaryPage getLastRecord(Context context) {
        Cursor cursor = new BetezDiaryDb(context).getLastRecord();
        if (cursor.getCount() == 0) {
            return new DiaryPage("2018-05-14", "");
        } else {
            cursor.moveToFirst();
            return new DiaryPage(cursor.getString(cursor.getColumnIndex("date")), cursor.getString(cursor.getColumnIndex("content")));
        }
    }

    public static DiaryPage getToday(Context context) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Cursor today = new BetezDiaryDb(context).getPageByDate(LocalDate.now().format(formatter));
        Log.d("cg", Integer.toString(today.getCount()));
        if (today.getCount() == 0){
            return new DiaryPage(LocalDate.now(), "");
        }
        else {
            today.moveToFirst();
            return new DiaryPage(today.getString(today.getColumnIndex("date")), today.getString(today.getColumnIndex("content")));
        }
    }
}
