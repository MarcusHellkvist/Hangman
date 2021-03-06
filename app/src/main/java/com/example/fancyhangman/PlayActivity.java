package com.example.fancyhangman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private static final String TAG = "MACO";

    // SINGLETON CLASS
    private GameLogic game;
    private SharedPreferences sh;

    // VARIABLES
    private final int MAX_GUESSES = 9;
    private int currentGuess = 0;
    private String[] words;
    private String currentWord;
    private char[] hiddenWord;
    private ArrayList<String> guessedLetters = new ArrayList<>();
    private String theme;

    // VIEWS
    private TextView tvHiddenWord;
    private TextView tvTriesLeft;
    private TextView tvGuessedLetters;
    private ImageView ivHangman;
    private Button btnÅ;
    private Button btnÄ;
    private Button btnÖ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // ENABLE BACK BUTTON ON ACTIONBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // GET CURRENT LANGUAGE
        sh = getSharedPreferences(MainActivity.MY_KEY, MODE_PRIVATE);

        String currentLanguage = sh.getString("language", "");
        theme = sh.getString("theme", "Cartoon");

        // GET SINGLETON INSTANCE
        game = GameLogic.getInstance();

        // BIND VIEWS
        tvHiddenWord = findViewById(R.id.tv_hidden_word);
        tvTriesLeft = findViewById(R.id.tv_triesLeft);
        tvGuessedLetters = findViewById(R.id.tv_guessedLetters);
        btnÅ = findViewById(R.id.btn_å);
        btnÄ = findViewById(R.id.btn_ä);
        btnÖ = findViewById(R.id.btn_ö);
        ivHangman = findViewById(R.id.iv_hangman);

        // SHOW CORRECT KEYBOARD DEPENDING ON CURRENT LANGUAGE
        showKeyboard(currentLanguage);

        // FIND, HIDE, AND SHOW A RANDOM WORD
        words = getResources().getStringArray(R.array.words);
        currentWord = getRandomWord(words);
        hideWord(currentWord);
        updateAllText();

        // FLUSH LIST OF LETTERS
        guessedLetters.clear();

    }

    private void showKeyboard(String currentLanguage) {
        if (currentLanguage.contentEquals("en")){
            btnÅ.setVisibility(View.INVISIBLE);
            btnÄ.setVisibility(View.INVISIBLE);
            btnÖ.setVisibility(View.INVISIBLE);
        }
    }

    private void updateAllText() {
        String s = new String(hiddenWord);
        tvHiddenWord.setText(s);
        String triesLeft = getString(R.string.guesses_left_text);
        tvTriesLeft.setText(triesLeft + (MAX_GUESSES - currentGuess));
        String lettersGuessed = getString(R.string.letters_guessed_text);
        tvGuessedLetters.setText(lettersGuessed + guessedLetters);
    }

    private void hideWord (String wordToHide){
        hiddenWord = new char[wordToHide.length()];
        for (int i = 0; i < hiddenWord.length; i++) {
            hiddenWord[i] = '_';
        }
    }

    private String getRandomWord(String[] sArray){
        Random r = new Random();
        int rNumber = r.nextInt(sArray.length);
        return sArray[rNumber];
    }

    private boolean checkWin() {
        return currentWord.contentEquals(String.valueOf(hiddenWord));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ac_info_icon) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void guessLetter(View view) {
        Button b = (Button) view;
        String letter = b.getText().toString().toUpperCase().trim();

        //CHECK IF LETTER HAS BEEN GUESSED BEFORE
        if (guessedLetters.contains(letter)){
            Toast.makeText(this, R.string.already_guessed, Toast.LENGTH_SHORT).show();
            currentGuess++;
            loadImages(ivHangman, currentGuess, theme);
        } else {

            //CHECK IF THE WORD CONTAINS THE LETTER
            if (currentWord.contains(letter)){
                b.setEnabled(false);
                b.setBackgroundResource(R.drawable.btninactive);
                int index = currentWord.indexOf(letter);
                while (index >= 0) {
                    hiddenWord[index] = letter.charAt(0);
                    index = currentWord.indexOf(letter, index + 1);
                }
            } else {
                Toast.makeText(this, R.string.letter_not_exist, Toast.LENGTH_SHORT).show();
                guessedLetters.add(letter);
                currentGuess++;
                loadImages(ivHangman, currentGuess, theme);

            }
        }

        updateAllText();

        // CHECK FOR WIN
        if (checkWin()){
            Intent intent = new Intent(this, EndActivity.class);
            game.setWinState(1);
            game.setFinalWord(currentWord);
            game.setAmountOfGuesses(currentGuess);

            startActivity(intent);
            finish();

        } else if (currentGuess >= MAX_GUESSES){
            Intent intent = new Intent(this, EndActivity.class);
            game.setWinState(2);
            game.setFinalWord(currentWord);
            game.setAmountOfGuesses(currentGuess);

            startActivity(intent);
            finish();
        }

    }

    private void loadImages(ImageView view, int i, String theme){

        if (theme.contentEquals("Cartoon") || theme.contentEquals("Tecknad")){
            Glide.with(this)
                    .load("https://raw.githubusercontent.com/MarcusHellkvist/Hangman/main/pictures/hangman_" + i + ".png")
                    .override(200, 200)
                    .into(view);
        } else {
            Glide.with(this)
                    .load("https://raw.githubusercontent.com/MarcusHellkvist/Hangman/main/pictures/bitman/bitman_" + i + ".png")
                    .override(200, 200)
                    .into(view);
        }
    }
}
