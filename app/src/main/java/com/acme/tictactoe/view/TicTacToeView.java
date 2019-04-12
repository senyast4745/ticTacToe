package com.acme.tictactoe.view;

public interface TicTacToeView {
    void showWinner(String winningPlayerDisplayLabel);
    void clearWinnerDisplay();
    void setProgressVisibility(boolean visible);
    void clearButtons();
    void setButtonText(int row, int col, String text);
}
