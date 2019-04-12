package com.acme.tictactoe.presenter;

public interface TicTacToePresenterInterface {
    void onButtonSelected(int row, int col);

    void onResetSelected();
}
