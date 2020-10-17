package com.example.fancyhangman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class EndActivity extends AppCompatActivity {

    private static final String TAG = "MACO";

    private TextView tvEndText, tvCorrectWord, tvAmountOfGuesses;
    private LottieAnimationView trophy;
    private LottieAnimationView confetti;

    private GameLogic game;

    private Button btnPlayAgain, btnExitGame;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        game = GameLogic.getInstance();

        mediaPlayer = MediaPlayer.create(this, R.raw.odetojoy);

        // VIEW BINDING
        tvEndText = findViewById(R.id.tv_end_text);
        tvCorrectWord = findViewById(R.id.tv_correct_word);
        tvAmountOfGuesses = findViewById(R.id.tv_amount_of_guesses);
        trophy = findViewById(R.id.animation_view_trophy);
        confetti = findViewById(R.id.animation_view_confetti_falling);
        btnPlayAgain = findViewById(R.id.btn_menu_play);
        btnExitGame = findViewById(R.id.btn_menu_exit);

        // BUTTON LISTENERS
        btnPlayAgain.setOnClickListener(playAgain);
        btnExitGame.setOnClickListener(exitGame);

        int winState = game.getWinState();
        int amountOfGuesses = game.getAmountOfGuesses();
        String finalWord = game.getFinalWord();

        if (winState == 1){
            tvEndText.setText(R.string.you_win);
            playWinState();
        } else {
            tvEndText.setText(R.string.you_lose);
        }

        tvCorrectWord.setText(getString(R.string.the_word_was) + finalWord);
        tvAmountOfGuesses.setText(getString(R.string.number_of_guesses) + amountOfGuesses);
    }

    View.OnClickListener playAgain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(EndActivity.this, PlayActivity.class);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener exitGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            System.exit(0);
        }
    };

    public void playWinState(){
        mediaPlayer.start();
        trophy.playAnimation();
        confetti.playAnimation();

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
                Intent i1 = new Intent(EndActivity.this, PlayActivity.class);
                startActivity(i1);
                finish();
                break;
            case R.id.ac_info_icon:
                Intent i2 = new Intent(this, SettingsActivity.class);
                startActivity(i2);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
