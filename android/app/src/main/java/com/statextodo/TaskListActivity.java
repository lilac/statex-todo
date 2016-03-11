package com.statextodo;

/**
 * @author Junjun Deng 2016
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import co.rewen.statex.StateXPackage;
import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

public class TaskListActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler
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
			.setJSMainModuleName("events")
			.addPackage(new MainReactPackage())
			.addPackage(new StateXPackage())
			.setUseDeveloperSupport(BuildConfig.DEBUG)
			.setInitialLifecycleState(LifecycleState.RESUMED)
			.build();

		setContentView(R.layout.task_list);

		mBroadcastManager = LocalBroadcastManager.getInstance(this);

		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
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

	@Override
	public void onBackPressed()
	{
		if(mReactInstanceManager != null)
		{
			mReactInstanceManager.onBackPressed();
		}
		else
		{
			super.onBackPressed();
		}
	}

	@Override
	public void invokeDefaultOnBackPressed()
	{
		super.onBackPressed();
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		if(mReactInstanceManager != null)
		{
			mReactInstanceManager.onPause();
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		if(mReactInstanceManager != null)
		{
			mReactInstanceManager.onResume(this, this);
		}
	}
}
