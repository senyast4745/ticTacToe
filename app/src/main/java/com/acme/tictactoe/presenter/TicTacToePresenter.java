package com.acme.tictactoe.presenter;

import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;
import com.acme.tictactoe.view.TicTacToeView;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

public class TicTacToePresenter implements TicTacToePresenterInterface {

    private final Executor mainThreadExecutor;
    private final Executor backgroundThreadExecutor;
    private final Board model;
    private TicTacToeView view;

    @Inject
    public TicTacToePresenter(@Named("MainThreadExecutor") Executor mainThreadExecutor, @Named("BackgroundThreadExecutor") Executor backgroundThreadExecutor, Board board) {
        this.mainThreadExecutor = mainThreadExecutor;
        this.backgroundThreadExecutor = backgroundThreadExecutor;
        this.model = board;
    }

    @Override
    public void setView(TicTacToeView view) {
        this.view = view;
    }

    @Override
    public void onButtonSelected(int row, int col) {
        view.setProgressVisibility(true);
        backgroundThreadExecutor.execute(() -> mark(row, col));
    }

    private void mark(int row, int col) {
        Player playerThatMoved = model.mark(row, col);
        mainThreadExecutor.execute(() -> onCellMarked(row, col, playerThatMoved));
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
