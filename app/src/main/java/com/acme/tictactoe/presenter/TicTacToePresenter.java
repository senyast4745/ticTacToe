package com.acme.tictactoe.presenter;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;
import com.acme.tictactoe.view.TicTacToeView;

public class TicTacToePresenter implements TicTacToePresenterInterface {

    private final TicTacToeView view;
    private Board model;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public TicTacToePresenter(TicTacToeView view) {
        this.view = view;
        this.model = new Board();
    }

    @Override
    public void onButtonSelected(int row, int col) {
        view.setProgressVisibility(true);
        AsyncTask.SERIAL_EXECUTOR.execute(() -> mark(row, col));
    }

    private void mark(int row, int col) {
        Player playerThatMoved = model.mark(row, col);
        handler.post(() -> onCellMarked(row, col, playerThatMoved));
    }

    private void onCellMarked(int row, int col, Player playerThatMoved) {
        view.setProgressVisibility(false);
        if(playerThatMoved != null) {
            view.setButtonText(row, col, playerThatMoved.toString());

            if (model.getWinner() != null) {
                view.showWinner(playerThatMoved.toString());
            }
        }
    }

    @Override
    public void onResetSelected() {
        view.clearWinnerDisplay();
        view.clearButtons();
        model.restart();
    }


}
