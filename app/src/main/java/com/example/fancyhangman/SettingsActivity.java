package com.example.fancyhangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    Spinner spinnerLanguage;
    Button btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // VIEW BINDING
        spinnerLanguage = findViewById(R.id.sp_language);
        btnApply = findViewById(R.id.btn_settings_language);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String language = spinnerLanguage.getSelectedItem().toString();
                if (language.contentEquals("Swedish") || language.contentEquals("Svenska")){
                    setLocale("sv");
                } else {
                    setLocale("en");
                }*/

            }
        });
    }

    private void setLocale(String language){

        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(SettingsActivity.this, SettingsActivity.class);
        finish();
        startActivity(refresh);
    }
}