package com.betezteam.betezdiary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.betezteam.DiaryModel.Diary;
import com.betezteam.DiaryModel.DiaryPage;

public class DiaryPageActivity extends AppCompatActivity {

    public DiaryPage getMainDiaryPage() {
        return mainDiaryPage;
    }

    public void setMainDiaryPage(DiaryPage mainDiaryPage) {
        this.mainDiaryPage = mainDiaryPage;
    }

    private DiaryPage mainDiaryPage;
    private TextView mainDate;
    private TextView mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_page);

        mainDiaryPage = Diary.getToday(this);

        mainDate = findViewById(R.id.main_date);
        mainDate.requestFocus();

        mainContent = findViewById(R.id.main_content);
        Typeface scriptFont = Typeface.createFromAsset(getAssets(), "DancingScript-Regular.ttf");
        mainContent.setTypeface(scriptFont);

        CGButton cgButton = new CGButton(this);

        assignDateChanger();
    }

    public void overView(View view) {
        Intent actInt = new Intent(this, OverViewActivity.class);
        startActivity(actInt);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mainDate = findViewById(R.id.main_date);
        mainDate.setText(mainDiaryPage.getDateDisplayed());

        mainContent = findViewById(R.id.main_content);
        mainContent.setText(mainDiaryPage.getContent());

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        Bundle getThrough = getIntent().getExtras();
//
//        if (getThrough == null || !getThrough.containsKey("get_through") || !getThrough.getBoolean("get_through")) {
//                Intent actInt = new Intent(this, LockActivity.class);
//                startActivity(actInt);
//        }
//        else {
//            getIntent().putExtra("get_through", false);
//        }
//
//
//    }

    private void lock(){
        Bundle getThrough = getIntent().getExtras();

        if (getThrough == null || !getThrough.containsKey("get_through") || !getThrough.getBoolean("get_through")) {
                Intent actInt = new Intent(this, LockActivity.class);
                startActivity(actInt);
        }
        else {
            getIntent().putExtra("get_through", false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainDiaryPage.setContent(mainContent.getText().toString());
        mainDiaryPage.submit(this);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void assignDateChanger() {
        ScrollView viewport = findViewById(R.id.diary_page_view);
        viewport.setOnTouchListener(new View.OnTouchListener() {
            static final int MIN_DISTANCE = 150;
            private int x1, x2;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = (int) event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = (int) event.getX();
                        float deltaX = x2 - x1;
                        if (deltaX > MIN_DISTANCE)
                        {
                            mainDiaryPage = Diary.getPreviousPage(mainDiaryPage, DiaryPageActivity.this);

                            mainDate = findViewById(R.id.main_date);
                            mainDate.setText(mainDiaryPage.getDateDisplayed());

                            mainContent = findViewById(R.id.main_content);
                            mainContent.setText(mainDiaryPage.getContent());
                        }
                        else if(deltaX < 0 - MIN_DISTANCE){
                            mainDiaryPage = Diary.getNextPage(mainDiaryPage, DiaryPageActivity.this);

                            mainDate = findViewById(R.id.main_date);
                            mainDate.setText(mainDiaryPage.getDateDisplayed());

                            mainContent = findViewById(R.id.main_content);
                            mainContent.setText(mainDiaryPage.getContent());
                        }
                        else
                        {
                            // consider as something else - a screen tap for example
                        }

                        break;
                }
                return true;
            }
        });
    }



}