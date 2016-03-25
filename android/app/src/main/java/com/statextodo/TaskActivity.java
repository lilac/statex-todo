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
import co.rewen.react.notification.NotificationPackage;
import co.rewen.statex.StateXPackage;
import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import org.pgsqlite.SQLitePluginPackage;

public class TaskActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler,
		HasReactInstance
{
	private static final String TASKS_PATH = "/task";

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
		route();
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

	private void route() {
		Fragment fragment;
		String location = Store.getState(this, Constants.LOCATION);
		if(location == null) location = "/"; // default to root path

		if(TASKS_PATH.equals(location)) {
			fragment = new TaskListFragment();
		} else {
			fragment = new WelcomeFragment();
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		if(fragmentManager.findFragmentByTag(location) == null) {
			fragmentManager.beginTransaction()
					.replace(R.id.frame, fragment, location)
					.commit();
		}
	}

	private BroadcastReceiver router = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			route();
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
		mBroadcastManager.registerReceiver(router, Utils.getStateFilter(Constants.LOCATION));
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		if(mReactInstanceManager != null)
		{
			mReactInstanceManager.onPause();
		}
		mBroadcastManager.unregisterReceiver(router);
	}

	@Override
	public ReactInstanceManager getReactInstance()
	{
		return mReactInstanceManager;
	}
}
