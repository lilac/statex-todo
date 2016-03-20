package com.statextodo;

/**
 * @author Junjun Deng 2016
 */
public class Task
{
	long id;
	String title;
	boolean completed;

	public Task(long id, String title, boolean completed)
	{
		this.id = id;
		this.title = title;
		this.completed = completed;
	}
}
