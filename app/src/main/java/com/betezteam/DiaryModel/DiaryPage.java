package com.betezteam.DiaryModel;

import android.content.Context;

import com.betezteam.util.AESHelper;
import com.betezteam.util.BetezDiaryDb;
import com.betezteam.util.SHA256Helper;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.Serializable;

public class DiaryPage implements Serializable{

    private LocalDate date;
    private String content;

    public DiaryPage(LocalDate date, String content) {
        this.date = date;
        this.content = content;
    }

    public DiaryPage(String date, String content) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = LocalDate.parse(date, formatter);

        this.content = content;
    }


    public String getDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return this.date.format(formatter);
    }

    public String getDateDisplayed() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return this.date.format(formatter);
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void submit(Context context) {

        if(content.trim().isEmpty()) { //Không lưu nếu DL trống
            new BetezDiaryDb(context).delete(getDateString());
            return;
        }
        // TODO: 6/10/2018 test this part, turn into delete data

        String encryptedContent = "";
        try {
            // TODO: 6/13/2018  change seed to user id
            String userId = "seed";
            String encryptKey = SHA256Helper.SHA256WithSalt(userId + "BETEZTEAM", "betez2001");
            encryptedContent = AESHelper.encrypt(encryptKey, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new BetezDiaryDb(context).submmit(getDateString(), encryptedContent);
    }
}
