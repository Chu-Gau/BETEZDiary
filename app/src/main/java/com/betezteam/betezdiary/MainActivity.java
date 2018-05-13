package com.betezteam.betezdiary;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mainContent = findViewById(R.id.main_content);
        Typeface scriptFont = Typeface.createFromAsset(getAssets(), "DancingScript-Regular.ttf");
        mainContent.setTypeface(scriptFont);
    }

    public void overView(View view) {
        Intent actInt = new Intent(this, OverViewActivity.class);
        startActivity(actInt);
    }

    @Override
    protected void onResume() {
        super.onResume();
//
//        Bundle getThrough = getIntent().getExtras();
//
//        if (getThrough == null || !getThrough.containsKey("get_through") || !getThrough.getBoolean("get_through")) {
//                Intent actInt = new Intent(this, LockActivity.class);
//                startActivity(actInt);
//        }

    }
}