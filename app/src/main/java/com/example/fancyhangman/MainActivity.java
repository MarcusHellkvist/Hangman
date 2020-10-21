package com.example.fancyhangman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MACO";
    public static final String MY_KEY = "MY_PREFS_KEY";
    private Button btnPlay, btnAbout, btnExit;

    private SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sh = getSharedPreferences(MY_KEY, MODE_PRIVATE);

        String lang = sh.getString("language", "");
        String theme = sh.getString("theme", "cartoon");

        Log.d(TAG, "onCreate: " + lang);

        loadLocale();
        setContentView(R.layout.activity_main); // this cant be a good solution!

        //BIND VIEWS
        btnPlay = findViewById(R.id.btn_menu_play);
        btnAbout = findViewById(R.id.btn_menu_about);
        btnExit = findViewById(R.id.btn_menu_exit);

        //BUTTON LISTENERS
        btnPlay.setOnClickListener(playGameListener);
        btnAbout.setOnClickListener(aboutGameListener);
        btnExit.setOnClickListener(exitGameListener);

    }

    View.OnClickListener playGameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, PlayActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener aboutGameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener exitGameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            System.exit(0);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ac_play_icon:
                Intent i1 = new Intent(this, PlayActivity.class);
                startActivity(i1);
                break;
            case R.id.ac_info_icon:
                Intent i2 = new Intent(this, SettingsActivity.class);
                startActivity(i2);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadLocale(){
        String language = sh.getString("language", "");
        changeLang(language);
    }

    private void changeLang(String lang){

        if (lang.equalsIgnoreCase("")){
            return;
        }
        Locale myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

    }

    public void saveLocale(String lang){
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("language", lang);
        editor.apply();
    }
}
