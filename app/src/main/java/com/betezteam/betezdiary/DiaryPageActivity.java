package com.betezteam.betezdiary;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    //
//    private class customEditext extends android.support.v7.widget.AppCompatEditText{
//        public customEditext(Context context) {
//            super(context);
//        }
//
//        public customEditext(Context context, AttributeSet attrs) {
//            super(context, attrs);
//        }
//
//        public customEditext(Context context, AttributeSet attrs, int defStyleAttr) {
//            super(context, attrs, defStyleAttr);
//        }
//
//        public customEditext(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//            super(context, attrs, defStyleAttr, defStyleRes);
//        }
//
//        @Override
//        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
//            if (keyCode == KeyEvent.KEYCODE_BACK &&
//                    event.getAction() == KeyEvent.ACTION_UP) {
//                // do your stuff
//
//                return false;
//            }
//            return super.dispatchKeyEvent(event);
//
//        }
//    }


}