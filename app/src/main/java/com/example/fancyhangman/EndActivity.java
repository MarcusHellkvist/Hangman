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

    private Button btnPlayAgain;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        mediaPlayer = MediaPlayer.create(this, R.raw.odetojoy);

        // VIEW BINDING
        tvEndText = findViewById(R.id.tv_end_text);
        tvCorrectWord = findViewById(R.id.tv_correct_word);
        tvAmountOfGuesses = findViewById(R.id.tv_amount_of_guesses);
        trophy = findViewById(R.id.animation_view_trophy);
        confetti = findViewById(R.id.animation_view_confetti_falling);
        btnPlayAgain = findViewById(R.id.btn_menu_play);

        // GET DATA FROM PLAYACTIVITY
        Intent intent = getIntent();
        String mainText = intent.getStringExtra(PlayActivity.SCORETEXT_KEY);
        int winState = intent.getIntExtra(PlayActivity.WINSTATE_KEY, 0);
        String wordText = intent.getStringExtra(PlayActivity.WORD_KEY);
        int guessText = intent.getIntExtra(PlayActivity.GUESSES_KEY, 0);

        btnPlayAgain.setOnClickListener(playAgain);

        if (winState == 1){
            playWinState();
        }
        
        // SET TEXT
        tvEndText.setText(mainText);
        String wordTextString = getString(R.string.the_word_was) + wordText;
        tvCorrectWord.setText(wordTextString);
        String guessTextString = getString(R.string.number_of_guesses) + guessText;
        tvAmountOfGuesses.setText(guessTextString);
    }

    View.OnClickListener playAgain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(EndActivity.this, PlayActivity.class);
            startActivity(intent);
            finish();
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
                Intent intent = new Intent(EndActivity.this, PlayActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.ac_info_icon:
                Intent intent1 = new Intent(this, SettingsActivity.class);
                startActivity(intent1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
