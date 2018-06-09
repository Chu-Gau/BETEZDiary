package com.betezteam.DiaryModel;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.betezteam.util.BetezDiaryDb;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;

public class Diary extends ArrayList<DiaryPage> {
    public Diary() {
        super();
    }

    public static DiaryPage getToday(Context context) {
        return getPageByDate(LocalDate.now(), context);
    }

    public static DiaryPage getPageByDate(LocalDate date, Context context) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Cursor thisDay = new BetezDiaryDb(context).getPageByDate(date.format(formatter));
        if (thisDay.getCount() == 0) {
            return new DiaryPage(date, "");
        } else {
            thisDay.moveToFirst();
            return new DiaryPage(thisDay.getString(thisDay.getColumnIndex("date")), thisDay.getString(thisDay.getColumnIndex("content")));
        }
    }

    public static DiaryPage getNextPage(DiaryPage page, Context context) {
        LocalDate date = page.getDate();
        date = date.plusDays(1);
        return getPageByDate(date, context);
    }

    public static DiaryPage getPreviousPage(DiaryPage page, Context context) {
        LocalDate date = page.getDate();
        date = date.minusDays(1);
        return getPageByDate(date, context);
    }

    public static ArrayList<DiaryPage> getTop30(Context context) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ArrayList<DiaryPage> ret = new ArrayList<>();
        Cursor last30Days = new BetezDiaryDb(context).getTop30();
        if (last30Days.getCount() != 0) {
            last30Days.moveToFirst();
            while (!last30Days.isAfterLast()) {
                ret.add(new DiaryPage(last30Days.getString(last30Days.getColumnIndex("date")), last30Days.getString(last30Days.getColumnIndex("content"))));
                last30Days.moveToNext();
            }
        }
        return ret;
    }
}
