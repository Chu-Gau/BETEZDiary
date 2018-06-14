package com.betezteam.betezdiary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;

import com.betezteam.DiaryModel.Diary;
import com.betezteam.DiaryModel.DiaryPage;
import com.betezteam.util.DiaryPageAdapter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;

public class OverViewActivity extends AppCompatActivity {
    private CalendarView datePicker;
    private ImageButton datePickerButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_view);

        getData();

        datePicker = findViewById(R.id.date_picker);
        datePickerButon = findViewById(R.id.date_picker_button);

        assignDatepicker();

        datePicker.setVisibility(View.GONE);
    }

    private void assignDatepicker() {
        datePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                LocalDate date = LocalDate.of(year, month, dayOfMonth);
                DiaryPage page = Diary.getPageByDate(date, OverViewActivity.this);

                Intent intent = new Intent(OverViewActivity.this, DiaryPageActivity.class);
                intent.putExtra("diaryPage", page);
                startActivity(intent);
            }
        });
        datePickerButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datePicker.getVisibility() == View.VISIBLE) {
                    datePicker.setVisibility(View.GONE);
                } else {
                    datePicker.setVisibility(View.VISIBLE);
                    datePicker.startAnimation(AnimationUtils.loadAnimation(OverViewActivity.this, R.anim.cg_layer2_anim));

                }
            }
        });
    }

    private void getData() {
        ArrayList<DiaryPage> top30 = Diary.getTop30(this);

        DiaryPageAdapter diaryPageAdapter = new DiaryPageAdapter(this, top30);

        ListView diaryTop30 = findViewById(R.id.top_30_list);

        diaryTop30.setAdapter(diaryPageAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        datePicker.setVisibility(View.GONE);
    }
}
