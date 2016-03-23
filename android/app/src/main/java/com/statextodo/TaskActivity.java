package com.statextodo;

/**
 * @author Junjun Deng 2016
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

import org.pgsqlite.SQLitePluginPackage;

import co.rewen.react.notification.NotificationPackage;
import co.rewen.statex.StateXPackage;

public class TaskActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler,
		HasReactInstance
{
	private ReactInstanceManager mReactInstanceManager;

	private LocalBroadcastManager mBroadcastManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mReactInstanceManager = ReactInstanceManager.builder()
			.setApplication(getApplication())
				//.setBundleAssetName("index.android.bundle")
			.setJSMainModuleName("index")
			.addPackage(new MainReactPackage())
			.addPackage(new StateXPackage())
			.addPackage(new SQLitePluginPackage(this))
			.addPackage(new NotificationPackage())
			.setUseDeveloperSupport(BuildConfig.DEBUG)
			.setInitialLifecycleState(LifecycleState.RESUMED)
			.build();

		mReactInstanceManager.createReactContextInBackground();
		setContentView(R.layout.toolbar_frame);

		mBroadcastManager = LocalBroadcastManager.getInstance(this);

		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		update();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.add_task:
				getSupportFragmentManager().beginTransaction()
					.replace(R.id.frame, new TaskFragment())
					.addToBackStack(null)
					.commit();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyUp(int keyCode, @NonNull KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null)
		{
			mReactInstanceManager.showDevOptionsDialog();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	private void update() {
		Fragment fragment;
		String tag;
		String value = new Store(this).getState("initialized");
		boolean initialized = Boolean.parseBoolean(value);
		if(initialized) {
			fragment = new TaskListFragment();
			tag = "taskList";
		} else {
			fragment = new WelcomeFragment();
			tag = "welcome";
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		if(fragmentManager.findFragmentByTag(tag) == null) {
			fragmentManager.beginTransaction()
					.replace(R.id.frame, fragment, tag)
					.commit();
		}

	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			update();
		}
	};

	@Override
	public void invokeDefaultOnBackPressed()
	{
		super.onBackPressed();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		if(mReactInstanceManager != null)
		{
			mReactInstanceManager.onResume(this, this);
		}
		mBroadcastManager.registerReceiver(receiver, Utils.getStateFilter("initialized"));
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		if(mReactInstanceManager != null)
		{
			mReactInstanceManager.onPause();
		}
		mBroadcastManager.unregisterReceiver(receiver);
	}

	@Override
	public ReactInstanceManager getReactInstance()
	{
		return mReactInstanceManager;
	}
}
