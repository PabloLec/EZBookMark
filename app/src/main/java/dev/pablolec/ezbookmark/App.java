package dev.pablolec.ezbookmark;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Application sApplication;
    public static Application getApplication() {
        return sApplication;
    }
    public static Context getContext() {
        return getApplication().getApplicationContext();
    }
    public static final String LOCAL_DATABASE = "LOCAL_DATABASE";

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}