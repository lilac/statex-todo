package com.statextodo;

import android.app.Application;
import com.facebook.stetho.Stetho;

/**
 * @author Junjun Deng 2016
 */
public class TaskApplication extends Application
{
	public void onCreate() {
		super.onCreate();
		Stetho.initializeWithDefaults(this);
	}
}
