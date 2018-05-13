package com.betezteam.DiaryModel;

import android.content.Context;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.util.Log;

import com.betezteam.util.BetezDiaryDb;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class DiaryList extends ArrayList<DiaryPage> {
    public DiaryList() {
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

}
