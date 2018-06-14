package com.betezteam.betezdiary;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivitySetting extends AppCompatActivity {

    Spinner SpinnerFont,SpinnerSize;
    SharedPreferences sharePreferences;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharePreferences = this.getSharedPreferences("inforSetting", Context.MODE_PRIVATE);
        SpinnerFont = (Spinner) findViewById(R.id.spinnerFont);
        SpinnerSize = (Spinner) findViewById(R.id.spinnerSize);
        final ArrayList<String> arrayFont = new ArrayList<String>();
        arrayFont.add("Arial");
        arrayFont.add("Comic");
        arrayFont.add("DancingScript");
        arrayFont.add("Pala");
        arrayFont.add("TimesNewRoman");
        final ArrayList<String> arraySize = new ArrayList<String>();
        for (int i=10;i<=36;i=i+2){
            arraySize.add(Integer.toString(i));
        }

        final ArrayAdapter arrayAdapterFont = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayFont);
        arrayAdapterFont.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerFont.setAdapter(arrayAdapterFont);

        int selectedFontIndex = sharePreferences.getInt("FontIndex", 2);

        SpinnerFont.setSelection(selectedFontIndex);

        SpinnerFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences.Editor editor = sharePreferences.edit();
                String selected = arrayFont.get(i);
                editor.putInt("FontIndex",i);
                editor.putString("Font", selected);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        final ArrayAdapter arrayAdapterSize = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arraySize);
        arrayAdapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerSize.setAdapter(arrayAdapterSize);
        int selectedSizeIndex = sharePreferences.getInt("SizeIndex", 2);
        SpinnerSize.setSelection(selectedSizeIndex);
        SpinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences.Editor editor = sharePreferences.edit();
                editor.putInt("SizeIndex",i);
                int selected = Integer.parseInt(arraySize.get(i));
                editor.putInt("Size", selected);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

}
