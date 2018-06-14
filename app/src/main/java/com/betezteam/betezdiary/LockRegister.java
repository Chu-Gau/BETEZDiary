package com.betezteam.betezdiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.betezteam.util.SHA256Helper;

public class LockRegister extends AppCompatActivity {

    private String pass;

    private TextView passwordTitle;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_register);

        findViews();

        newPass();
    }

    private void newPass() {
        passwordTitle.setText("Nhập mật khẩu mới");
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (passwordField.length() == 4){
                    // TODO: 6/14/2018 set lock number
                    pass = passwordField.getText().toString();
                    reEnterPass();
                }
                else if (passwordField.length() > 4){
                    Toast.makeText(LockRegister.this, "Mật khẩu quá dài, tối đa 4 ký tự", Toast.LENGTH_LONG).show();
                    passwordField.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void reEnterPass(){
        passwordTitle.setText("Nhập lại mật khẩu mới");
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (passwordField.length() == 4){
                    // TODO: 6/14/2018 set lock number
                    if(passwordField.getText().toString().equals(pass)){
                        pass = SHA256Helper.SHA256WithSalt(pass, "BETEZTEAMkumatsuki");

                        SharedPreferences preferences = getSharedPreferences("BETEZDiaryPref", MODE_PRIVATE);
                        SharedPreferences.Editor edit= preferences.edit();

                        edit.putString("BETEZDiaryPassCode", pass);
                        edit.commit();

                        Log.d("cg", pass + passwordField.getText().toString());

                        Toast.makeText(LockRegister.this, "Đặt mật khẩu mới thành công!", Toast.LENGTH_LONG).show();
                        startDiary();
                        finish();
                    }
                    else {
                        Toast.makeText(LockRegister.this, "Mật khẩu sai!", Toast.LENGTH_LONG).show();
                        passwordField.setText("");
                    }
                }
                else if (passwordField.length() > 4){
                    Toast.makeText(LockRegister.this, "Mật khẩu sai!", Toast.LENGTH_LONG).show();
                    passwordField.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void findViews() {
        passwordField = findViewById(R.id.password_field);
        passwordTitle = findViewById(R.id.password_title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void startDiary() {
        Intent actInt = new Intent(this, DiaryPageActivity.class);
        actInt.putExtra("get_through", true);
        startActivity(actInt);
    }

}
