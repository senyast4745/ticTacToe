package com.acme.tictactoe.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.acme.tictactoe.R;
import com.acme.tictactoe.viewmodel.TicTacToeViewModel;

import java.util.Map;

public class TicTacToeActivity extends AppCompatActivity {

    private static String TAG = TicTacToeActivity.class.getName();

    private ViewGroup buttonGrid;
    private View winnerPlayerViewGroup;
    private TextView winnerPlayerLabel;
    private View progress;

    private TicTacToeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe);
        winnerPlayerLabel = findViewById(R.id.winnerPlayerLabel);
        winnerPlayerViewGroup = findViewById(R.id.winnerPlayerViewGroup);
        buttonGrid = findViewById(R.id.buttonGrid);
        progress = findViewById(R.id.progress);

        viewModel = ViewModelProviders.of(this).get(TicTacToeViewModel.class);

        viewModel.getCellsLiveData().observe(this, cells -> {
            updateCells(cells);
        });

        viewModel.getWinnerLiveData().observe(this, winner -> {
            if (!TextUtils.isEmpty(winner)) {
                showWinner(winner);
            } else {
                clearWinnerDisplay();
            }
        });

        viewModel.getProgressVisibilityLiveData().observe(this, this::setProgressVisibility);
    }

    private void updateCells(Map<String, String> cells) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String tag = "" + i + j;
                setButtonText(tag, cells.get(tag));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tictactoe, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                viewModel.onResetSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCellClicked(View v) {
        Button button = (Button) v;
        String tag = button.getTag().toString();
        int row = Integer.valueOf(tag.substring(0,1));
        int col = Integer.valueOf(tag.substring(1,2));
        Log.i(TAG, "Click Row: [" + row + "," + col + "]");

        viewModel.onClickedCellAt(row, col);
    }

    public void setButtonText(String tag, String text) {
        Button btn = buttonGrid.findViewWithTag(tag);
        if(btn != null) {
            btn.setText(text);
        }
    }

    public void setProgressVisibility(boolean visible) {
        progress.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    public void showWinner(String winningPlayerDisplayLabel) {
        winnerPlayerLabel.setText(winningPlayerDisplayLabel);
        winnerPlayerViewGroup.setVisibility(View.VISIBLE);
    }

    public void clearWinnerDisplay() {
        winnerPlayerViewGroup.setVisibility(View.GONE);
        winnerPlayerLabel.setText("");
    }
}
