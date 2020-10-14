package com.example.fancyhangman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    private static final String TAG = "MACO";

    private TextView tvEndText, tvCorrectWord, tvAmountOfGuesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.odetojoy);
        mediaPlayer.start();

        // VIEW BINDING
        tvEndText = findViewById(R.id.tv_end_text);
        tvCorrectWord = findViewById(R.id.tv_correct_word);
        tvAmountOfGuesses = findViewById(R.id.tv_amount_of_guesses);

        Intent intent = getIntent();
        String mainText = intent.getStringExtra(PlayActivity.SCORETEXT_KEY);
        String wordText = intent.getStringExtra(PlayActivity.WORD_KEY);
        int guessText = intent.getIntExtra(PlayActivity.GUESSES_KEY, 0);

        tvEndText.setText(mainText);
        tvCorrectWord.setText("The word was: " + wordText);
        tvAmountOfGuesses.setText("Number of guesses: " + guessText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ac_play_icon:
                Intent intent = new Intent(EndActivity.this, PlayActivity.class);
                startActivity(intent);
                break;
            case R.id.ac_info_icon:
                Log.d(TAG, "onOptionsItemSelected: about pressed!");
        }
        return super.onOptionsItemSelected(item);
    }
}
