package com.acme.tictactoe;

import android.app.Application;

import com.acme.tictactoe.di.AppComponent;
import com.acme.tictactoe.di.AppModule;
import com.acme.tictactoe.di.DaggerAppComponent;

public class AndroidApplication extends Application {
    public AppComponent getAppComponent() {
        return appComponent;
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }
}
