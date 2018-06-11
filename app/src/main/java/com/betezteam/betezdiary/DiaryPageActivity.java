package com.betezteam.betezdiary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.betezteam.DiaryModel.Diary;
import com.betezteam.DiaryModel.DiaryPage;
import com.betezteam.util.CGButton;

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

        assignEvents();

    }

    public void overView(View view) {
        Intent actInt = new Intent(this, OverViewActivity.class);
        startActivity(actInt);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent().hasExtra("diaryPage")){
            mainDiaryPage = (DiaryPage) getIntent().getExtras().getSerializable("diaryPage");
        }
        mainDate = findViewById(R.id.main_date);
        mainDate.setText(mainDiaryPage.getDateDisplayed());

        mainContent = findViewById(R.id.main_content);
        mainContent.setText(mainDiaryPage.getContent());

    }

    @SuppressLint("ClickableViewAccessibility")
    private void assignDateSwipe() {
        ScrollView viewport = findViewById(R.id.diary_page_view);
        viewport.setOnTouchListener(new View.OnTouchListener() {
            static final int MIN_DISTANCE = 150;
            private int x1, x2;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                mainDiaryPage.submit(DiaryPageActivity.this);
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

    private void assignDiarySync() {
        mainContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mainDiaryPage.setContent(mainContent.getText().toString());
            }
        });
    }

    private void assignEvents() {
        assignDateSwipe();
        assignDiarySync();
    }

}
