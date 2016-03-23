package com.statextodo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.io.File;

import co.rewen.statex.StateXDatabaseSupplier;

/**
 * @author Junjun Deng 2016
 */
public class Store
{
	private SQLiteDatabase database;
	private Context mContext;

	public Store(Context context)
	{
		database = open(context, "todos");
		mContext = context;
	}

	public static SQLiteDatabase open(Context context, String name) {
		File dbFile = context.getDatabasePath(name);
		return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY |
				SQLiteDatabase.CREATE_IF_NECESSARY);
	}

	public Cursor all() {
		return database.rawQuery("select id from tasks", null);
	}

	@Nullable
	public Task get(long id) {
		Cursor cursor = database.rawQuery("select * from tasks where id = ?", new String[]{ Long.toString(id) });
		if(cursor.moveToNext()) {
			String title = cursor.getString(1);
			boolean completed = cursor.getInt(2) > 0;
			return new Task(id, title, completed);
		}
		cursor.close();
		return null;
	}

	public String getState(String key) {
		SQLiteDatabase database = new StateXDatabaseSupplier(mContext).getReadableDatabase();
		Cursor cursor = database.rawQuery("select value from state where key = ?",
				new String[] {key});
		if(cursor.moveToFirst()) {
			return cursor.getString(0);
		}
		cursor.close();
		return null;
	}
}
