package com.acme.tictactoe.presenter;


import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.model.Player;
import com.acme.tictactoe.view.TicTacToeView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.Executor;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * There are a lot more tests we can and should write but for now, just a few smoke tests.
 */
@RunWith(MockitoJUnitRunner.class)
public class TicTacToePresenterIsolatedTests {

    private TicTacToePresenter presenter;

    @Mock
    private TicTacToeView view;
    @Mock
    private Board board;

    private Executor executor = Runnable::run;

    @Before
    public void setup() {
        presenter = new TicTacToePresenter(executor, executor, board);
        presenter.setView(view);
    }

    private void clickAndAssertValueAt(int row, int col, Player expectedValue) {
        when(board.mark(row, col)).thenReturn(expectedValue);

        presenter.onButtonSelected(row, col);

        verify(board).mark(row, col);
        verify(view).setButtonText(row, col, expectedValue.toString());
    }

    /**
     * This test will simulate and verify o is the winner.
     *
     *    X | X | X
     *    O |   |
     *      | O |
     */
    @Test
    public void test3inRowAcrossTopForX() {

        when(board.getWinner()).thenReturn(null);

        clickAndAssertValueAt(0,0, Player.X);
        verify(view, never()).showWinner(anyString());

        clickAndAssertValueAt(1,0, Player.O);
        verify(view, never()).showWinner(anyString());

        clickAndAssertValueAt(0,1, Player.X);
        verify(view, never()).showWinner(anyString());

        clickAndAssertValueAt(2,1, Player.O);
        verify(view, never()).showWinner(anyString());

        when(board.getWinner()).thenReturn(Player.X);

        clickAndAssertValueAt(0,2, Player.X);
        verify(view).showWinner("X");

    }


}
