package com.acme.tictactoe.di;

import android.os.AsyncTask;

import com.acme.tictactoe.model.Board;
import com.acme.tictactoe.presenter.TicTacToePresenter;
import com.acme.tictactoe.presenter.TicTacToePresenterInterface;
import com.acme.tictactoe.view.MainThreadExecutor;

import java.util.concurrent.Executor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Singleton
    @Provides
    @Named("MainThreadExecutor")
    public Executor provideMainThreadExecutor(MainThreadExecutor executor) {
        return executor;
    }

    @Singleton
    @Provides
    @Named("BackgroundThreadExecutor")
    public Executor provideBackgroundThreadExecutor() {
        return AsyncTask.SERIAL_EXECUTOR;
    }

    @Provides
    public TicTacToePresenterInterface providePresenter(@Named("MainThreadExecutor") Executor mainExecutor,
                                                        @Named("BackgroundThreadExecutor") Executor background,
                                                        Board board) {
        return new TicTacToePresenter(mainExecutor, background, board);
    }
}
