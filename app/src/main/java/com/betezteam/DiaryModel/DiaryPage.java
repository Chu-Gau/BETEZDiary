package com.betezteam.DiaryModel;

import android.content.Context;
import android.util.Log;

import com.betezteam.util.BetezDiaryDb;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DiaryPage {

    private Date date;
    private String content;

    public DiaryPage(Date date, String content) {
        this.date = date;
        this.content = content;
    }

    public DiaryPage(String date, String content) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.date = new Date(parser.parse(date).getTime());
        } catch (Exception e) {
        }

        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void submit(Context context) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        String textString = formater.format(date);
        new BetezDiaryDb(context).submmit(textString, content);
    }
}
