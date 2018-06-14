package com.betezteam.betezdiary;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.betezteam.DiaryModel.Diary;
import com.betezteam.DiaryModel.DiaryPage;
import com.betezteam.util.BitmapHelper;
import com.betezteam.util.CGButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiaryPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public DiaryPage getMainDiaryPage() {
        return mainDiaryPage;
    }

    public void setMainDiaryPage(DiaryPage mainDiaryPage) {
        this.mainDiaryPage = mainDiaryPage;
    }

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private DiaryPage mainDiaryPage;
    private TextView mainDate;
    private TextView mainContent;
    private CGButton cgButton;
    private DrawerLayout drawerLayout;
    private ImageButton menuButton;
    private NavigationView navigationView;
    private View headerView;
    private CircleImageView userAvt;
    private TextView userDisplayName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_page);

        mainDiaryPage = Diary.getToday(this);

        mainDate = findViewById(R.id.main_date);
        mainDate.requestFocus();

        mainContent = findViewById(R.id.main_content);

        cgButton = new CGButton(this);


        drawerLayout = findViewById(R.id.main_drawer_layout);
        menuButton = findViewById(R.id.menu_button);
        navigationView = findViewById(R.id.navigation_view);
        headerView = navigationView.inflateHeaderView(R.layout.drawer_header);
        navigationView.setNavigationItemSelectedListener(this);

        assignEvents();
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        userAvt = headerView.findViewById(R.id.avatar);
        userDisplayName = headerView.findViewById(R.id.full_name);

        fetchUserInfo();

    }

    private void fetchUserInfo() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        userDisplayName.setText(currentUser.getDisplayName());

        String avtUrl = currentUser.getPhotoUrl().toString();
//        Bitmap avt = BitmapHelper.getBitmapfromUrl(avtUrl);
//        if(avt!=null){
//            userAvt.setImageBitmap(avt);
//        }
        Picasso.get().load(avtUrl).into(userAvt);
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


    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        cgButton.getSpeechResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.UI_setting:{
                Intent intent = new Intent(DiaryPageActivity.this, ActivitySetting.class);
                startActivity(intent);
                onBackPressed();
                break;
            }

            case R.id.change_password:{
                Intent intent = new Intent(DiaryPageActivity.this, LockRegister.class);
                startActivity(intent);
                onBackPressed();
                break;
            }

            case  R.id.logout:{
                onBackPressed();
                SignInActivity.signOut();
                Intent intent = new Intent(this, LockActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.about:{
                Intent intent = new Intent(DiaryPageActivity.this, AboutAcivity.class);
                startActivity(intent);
                onBackPressed();
                break;
            }

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        // khi an back, neu navigation dang mo thi dong lai
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences("inforSetting", MODE_PRIVATE);
        String font = preferences.getString("Font", "DancingScript");

        Typeface scriptFont = Typeface.createFromAsset(getAssets(), font+".ttf");
        mainContent.setTypeface(scriptFont);

        int fontSize = preferences.getInt("Size", 24);
        mainContent.setTextSize((float)fontSize);
    }


}
