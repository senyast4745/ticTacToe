package com.acme.tictactoe.presenter;

import com.acme.tictactoe.view.TicTacToeView;

public interface TicTacToePresenterInterface {
    void setView(TicTacToeView view);

    void onButtonSelected(int row, int col);

    void onResetSelected();
}
