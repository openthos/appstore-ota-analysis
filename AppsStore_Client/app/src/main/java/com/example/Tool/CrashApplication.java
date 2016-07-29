package com.example.Tool;

import android.app.Application;

/**
 * @author
 * @mail:
 * @version
 * @buildTime 2012-12-5
 */
public class CrashApplication extends Application {
    public void onCreate() {
	super.onCreate();

	CrashHandler crashHandler = CrashHandler.getInstance();
	// rashHandler
	crashHandler.init(this);

    }
}
