package com.example.fancyhangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    Spinner spinnerLanguage, spinnerTheme;
    Button btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // VIEW BINDING
        spinnerLanguage = findViewById(R.id.sp_language);
        spinnerTheme = findViewById(R.id.sp_theme);
        btnApply = findViewById(R.id.btn_settings_language);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sh = getSharedPreferences(MainActivity.MY_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();

                String language = spinnerLanguage.getSelectedItem().toString();
                String theme = spinnerTheme.getSelectedItem().toString();

                editor.putString("language", language);
                editor.putString("theme", theme);
                editor.apply();

                /*if (language.contentEquals("Swedish") || language.contentEquals("Svenska")){
                    setLocale("sv");
                } else {
                    setLocale("en");
                }*/

                Toast.makeText(SettingsActivity.this, R.string.changes, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setLocale(String language){
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));

    }
}