package com.statextodo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

/**
 * @author Junjun Deng 2016
 */
public class TaskFragment extends Fragment
{
	TextView title;
	CheckBox completed;

	private ReactInstanceManager reactInstanceManager;

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		reactInstanceManager = ((HasReactInstance) context).getReactInstance();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.edit_task, container, false);
		title = (TextView) view.findViewById(R.id.title);
		completed = (CheckBox) view.findViewById(R.id.completed);

		return view;
	}

	@Override
	public void onPause()
	{
		super.onPause();
		WritableMap map = Arguments.createMap();
		map.putString("title", title.getText().toString());
		map.putBoolean("completed", completed.isChecked());
		Utils.sendEvent(reactInstanceManager, "task/add", map);
	}
}
