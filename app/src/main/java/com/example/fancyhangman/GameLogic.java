package com.example.fancyhangman;


public class GameLogic {
    private static final GameLogic ourInstance = new GameLogic();

    public static GameLogic getInstance() {
        return ourInstance;
    }

    private GameLogic() {
    }

    private int winState = 0;
    private String finalWord;
    private int amountOfGuesses;

    public int getWinState() {
        return winState;
    }

    public void setWinState(int winState) {
        this.winState = winState;
    }

    public String getFinalWord() {
        return finalWord;
    }

    public void setFinalWord(String finalWord) {
        this.finalWord = finalWord;
    }

    public int getAmountOfGuesses() {
        return amountOfGuesses;
    }

    public void setAmountOfGuesses(int amountOfGuesses) {
        this.amountOfGuesses = amountOfGuesses;
    }



}
