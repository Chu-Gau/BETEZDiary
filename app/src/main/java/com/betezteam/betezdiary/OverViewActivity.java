package com.betezteam.betezdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.betezteam.betezdiary.R;

public class OverViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_view);
    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Intent actInt = new Intent(this, DiaryPageActivity.class);
//        actInt.putExtra("get_through", true);
//        startActivity(actInt);
//    }
}
