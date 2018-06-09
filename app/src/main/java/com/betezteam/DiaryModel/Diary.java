package com.betezteam.DiaryModel;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.betezteam.util.AESHelper;
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
            String thisDate = thisDay.getString(thisDay.getColumnIndex("date"));
            // TODO: 6/10/2018 seed
            String thisContent = "";
            try {
                thisContent = AESHelper.decrypt("1808", thisDay.getString(thisDay.getColumnIndex("content")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new DiaryPage(thisDate, thisContent);
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
                String thisDate = last30Days.getString(last30Days.getColumnIndex("date"));
                // TODO: 6/10/2018 seed
                String thisContent = "";
                try {
                    thisContent = AESHelper.decrypt("1808", last30Days.getString(last30Days.getColumnIndex("content")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ret.add(new DiaryPage(thisDate, thisContent));
                last30Days.moveToNext();
            }
        }
        return ret;
    }
}
