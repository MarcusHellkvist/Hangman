package com.example.fancyhangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

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
}
