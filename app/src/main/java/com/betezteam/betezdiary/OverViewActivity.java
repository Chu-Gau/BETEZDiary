package com.betezteam.betezdiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.betezteam.DiaryModel.Diary;
import com.betezteam.DiaryModel.DiaryPage;
import com.betezteam.util.DiaryPageAdapter;

import java.util.ArrayList;

public class OverViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_view);

        getData();
    }

    private void getData(){
        ArrayList<DiaryPage> top30 = Diary.getTop30(this);

        DiaryPageAdapter diaryPageAdapter = new DiaryPageAdapter(this, top30);

        ListView diaryTop30 = findViewById(R.id.top_30_list);

        diaryTop30.setAdapter(diaryPageAdapter);

    }
}
