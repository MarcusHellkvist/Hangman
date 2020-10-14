package com.example.fancyhangman;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private static final String TAG = "MACO";
    private final int MAX_GUESSES = 10;
    public static final String SCORETEXT_KEY = "SCORETEXT_KEY";
    public static final String WORD_KEY = "WORD_KEY";
    public static final String GUESSES_KEY = "GUESSES_KEY";
    public static final String WINSTATE_KEY = "WINSTATE_KEY";

    private TextView tvHiddenWord;
    private TextView tvTriesLeft;
    private TextView tvGuessedLetters;

    private int currentGuess = 0;

    private String[] words;
    private String currentWord;
    private char[] hiddenWord;
    private ArrayList<String> guessedLetters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // BIND VIEWS
        tvHiddenWord = findViewById(R.id.tv_hidden_word);
        tvTriesLeft = findViewById(R.id.tv_triesLeft);
        tvGuessedLetters = findViewById(R.id.tv_guessedLetters);

        // FIND, HIDE, AND SHOW A RANDOM WORD
        words = getResources().getStringArray(R.array.words);
        currentWord = getRandomWord(words);
        hideWord(currentWord);
        updateAllText();

        // FLUSH LIST OF LETTERS
        guessedLetters.clear();

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
                // TODO update images
            }
        }

        updateAllText();

        if (checkWin()){
            Intent intent = new Intent(this, EndActivity.class);
            intent.putExtra(WINSTATE_KEY, 1);
            intent.putExtra(SCORETEXT_KEY, getString(R.string.you_win));
            intent.putExtra(WORD_KEY, currentWord);
            intent.putExtra(GUESSES_KEY, currentGuess);
            startActivity(intent);
            finish();
        } else if (currentGuess >= 10){
            Intent intent = new Intent(this, EndActivity.class);
            intent.putExtra(WINSTATE_KEY, 2);
            intent.putExtra(SCORETEXT_KEY, getString(R.string.you_lose));
            intent.putExtra(WORD_KEY, currentWord);
            intent.putExtra(GUESSES_KEY, currentGuess);
            startActivity(intent);
            finish();
        }

    }
}
