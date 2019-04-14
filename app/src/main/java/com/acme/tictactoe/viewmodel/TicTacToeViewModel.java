package com.acme.tictactoe.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;

import java.util.HashMap;
import java.util.Map;

public class TicTacToeViewModel extends ViewModel {

    private Board model;

    private final MutableLiveData<Map<String, String>> cellsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> winnerLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressVisibilityLiveData = new MutableLiveData<>();
    private Map<String, String> cells = new HashMap<>();

    public TicTacToeViewModel() {
        model = new Board();
        cellsLiveData.setValue(cells);
        winnerLiveData.setValue("");
        progressVisibilityLiveData.setValue(false);
    }

    public LiveData<Map<String, String>> getCellsLiveData() {
        return cellsLiveData;
    }

    public LiveData<String> getWinnerLiveData() {
        return winnerLiveData;
    }

    public LiveData<Boolean> getProgressVisibilityLiveData() {
        return progressVisibilityLiveData;
    }

    public void onResetSelected() {
        model.restart();
        cells = new HashMap<>();
        cellsLiveData.setValue(cells);
        winnerLiveData.setValue("");
        progressVisibilityLiveData.setValue(false);
    }

    public void onClickedCellAt(int row, int col) {
        progressVisibilityLiveData.setValue(true);
        AsyncTask.SERIAL_EXECUTOR.execute(() -> {
            Player playerThatMoved = model.mark(row, col);
            progressVisibilityLiveData.postValue(false);
            if (playerThatMoved != null) {
                cells = new HashMap<>(cells);
                cells.put(String.valueOf(row) + String.valueOf(col), playerThatMoved.toString());
                cellsLiveData.postValue(cells);
                winnerLiveData.postValue(model.getWinner() == null ? "" : model.getWinner().toString());
            }
        });
    }

}
