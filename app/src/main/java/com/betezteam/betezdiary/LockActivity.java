package com.betezteam.betezdiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class LockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        final EditText pass = findViewById(R.id.password_field);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence password, int i, int i1, int i2) {
                if (password.length() == 4){
                    if(password.toString().equals("1808")){
                        Intent actInt = new Intent(LockActivity.this, MainActivity.class);
                        actInt.putExtra("get_through", true);
                        startActivity(actInt);
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

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}
