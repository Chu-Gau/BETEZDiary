package com.betezteam.betezdiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.betezteam.util.SHA256Helper;

public class LockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        if(SignInActivity.isSignedIn())finish();

        // TODO: 6/11/2018 xóa đi trên bản release
//        startDiary(); //this is for test

    }

    @Override
    protected void onStart() {
        super.onStart();
        assignEvents();
    }

    private void assignEvents() {
        final EditText pass = findViewById(R.id.password_field);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = pass.getText().toString();
                if (password.length() == 4){
                    SharedPreferences preferences = getSharedPreferences("BETEZDiaryPref", MODE_PRIVATE);
                    String encryptedPass = preferences.getString("BETEZDiaryPassCode", null);
                    password = SHA256Helper.SHA256WithSalt(password.toString(), "BETEZTEAMkumatsuki");

                    // TODO: 6/14/2018 set lock number
                    if(password.toString().equals(encryptedPass)){
                        startDiary();
                        finish();
                    }
                    else {
                        Toast.makeText(LockActivity.this, "Mật khẩu sai!", Toast.LENGTH_LONG).show();
                        pass.setText("");
                    }
                }
                else if (password.length() > 4){
                    Toast.makeText(LockActivity.this, "Mật khẩu sai!", Toast.LENGTH_LONG).show();
                    pass.setText("");
                }
            }
        });
    }

    private void googleSignIn() {
        Intent i = new Intent(this, SignInActivity.class);
        Log.d("cg", "dm");
        startActivity(i);
//        finish();
    }

    private void startDiary() {
        Intent actInt = new Intent(LockActivity.this, DiaryPageActivity.class);
        actInt.putExtra("get_through", true);
        startActivity(actInt);
    }

}
