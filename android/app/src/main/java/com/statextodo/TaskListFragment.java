package com.statextodo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * @author Junjun Deng 2016
 */
public class TaskListFragment extends Fragment
{
	RecyclerView listView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.recycler_view, container, false);
		listView = (RecyclerView) view.findViewById(R.id.recycler_view);

		Store store = new Store(getContext());
		Cursor cursor = store.all();
		RecyclerView.Adapter adapter = new TaskListAdapter(cursor);
		listView.setAdapter(adapter);

		return view;
	}

	class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> {
		private Cursor cursor;
		private Store store;

		TaskListAdapter(Cursor cursor)
		{
			this.cursor = cursor;
			store = new Store(getContext());
		}

		@Override
		public long getItemId(int position)
		{
			cursor.moveToPosition(position);
			return cursor.getInt(0);
		}

		@Override
		public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
		{
			View view = View.inflate(parent.getContext(), R.layout.task_view, parent);
			return new TaskViewHolder(view);
		}

		@Override
		public void onBindViewHolder(TaskViewHolder holder, int position)
		{
			long id = getItemId(position);
			Task task = store.get(id);
			holder.setData(task);
		}

		@Override
		public int getItemCount()
		{
			return cursor.getCount();
		}
	}

	static class TaskViewHolder extends RecyclerView.ViewHolder {
		TextView title;
		CheckBox completed;

		public TaskViewHolder(View view)
		{
			super(view);
			title = (TextView) view.findViewById(R.id.title);
			completed = (CheckBox) view.findViewById(R.id.completed);
		}

		public void setData(Task task)
		{
			title.setText(task.title);
			completed.setChecked(task.completed);
		}
	}
}
