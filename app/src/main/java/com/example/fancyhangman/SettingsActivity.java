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

                if (language.contentEquals("Swedish") || language.contentEquals("Svenska")){
                    editor.putString("language", "sv");
                } else {
                    editor.putString("language", "en");
                }

                String theme = spinnerTheme.getSelectedItem().toString();

                editor.putString("theme", theme);
                editor.apply();

                Toast.makeText(SettingsActivity.this, R.string.changes, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));

            }
        });
    }
}