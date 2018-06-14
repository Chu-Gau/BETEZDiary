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

    private TextWatcher newPass;
    private TextWatcher reNewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_register);

        findViews();

        textWatcherInit();

        newPass();
    }

    private void textWatcherInit() {
        newPass = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (passwordField.length() == 4){
                    // TODO: 6/14/2018 set lock number
                    pass = passwordField.getText().toString().trim();
                    Log.d("cg3", pass);
                    passwordField.setText("");
                    reEnterPass();
                }
                else if (passwordField.length() > 4){
                    Toast.makeText(LockRegister.this, "Mật khẩu quá dài, tối đa 4 ký tự", Toast.LENGTH_LONG).show();
                    passwordField.setText("");
                }
            }
        };
        reNewPass = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String reenterPass = passwordField.getText().toString().trim();

                if (reenterPass.length() == 4){
                    // TODO: 6/14/2018 set lock number
                    if(reenterPass.equals(pass)){
                        pass = SHA256Helper.SHA256WithSalt(pass, "BETEZTEAMkumatsuki");

                        SharedPreferences preferences = getSharedPreferences("BETEZDiaryPref", MODE_PRIVATE);
                        SharedPreferences.Editor edit= preferences.edit();
                        edit.putString("BETEZDiaryPassCode", pass);
                        edit.commit();

                        Toast.makeText(LockRegister.this, "Đặt mật khẩu mới thành công!", Toast.LENGTH_LONG).show();
                        startDiary();
                        finish();
                    }
                    else {
                        Toast.makeText(LockRegister.this, "Mật khẩu không khớp!", Toast.LENGTH_LONG).show();
                        passwordField.setText("");
                    }
                }
                else if (passwordField.length() > 4){
                    Toast.makeText(LockRegister.this, "Mật khẩu không khớp!", Toast.LENGTH_LONG).show();
                    passwordField.setText("");
                }
            }
        };
    }


    private void newPass() {
        passwordTitle.setText("Nhập mật khẩu mới");
        passwordField.addTextChangedListener(newPass);
    }

    private void reEnterPass(){
        passwordField.removeTextChangedListener(newPass);
        passwordTitle.setText("Nhập lại mật khẩu mới");
        passwordField.addTextChangedListener(reNewPass);
    }

    private void findViews() {
        passwordField = findViewById(R.id.password_field);
        passwordTitle = findViewById(R.id.password_title);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Đặt mật khẩu mới thất bại. Vui lòng thử lại...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LockRegister.class);
        startActivity(intent);
        super.onBackPressed();
        finish();
    }

    private void startDiary() {
        Intent actInt = new Intent(this, DiaryPageActivity.class);
        actInt.putExtra("get_through", true);
        startActivity(actInt);
    }

}
