package org.tecnogame.hellopaper;

import android.app.Application;

import io.paperdb.Paper;

/**
 * Created by Alberto on 14/10/2015.
 */
public class HelloPaperApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
    }
}
