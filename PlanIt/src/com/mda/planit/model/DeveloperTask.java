package com.mda.planit.model;

import java.util.Date;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.util.Duration;

public class DeveloperTask {
	private Task task;
	private ListProperty<Date> beginTimes;
	private ListProperty<Date> endTimes;
	private ListProperty<String> comments;
	
	public DeveloperTask(Task task) {
		this.task = task;
		beginTimes = new SimpleListProperty<Date>(FXCollections.observableArrayList());
		endTimes = new SimpleListProperty<Date>(FXCollections.observableArrayList());
		comments = new SimpleListProperty<String>(FXCollections.observableArrayList());
	}
	
	public void add(Date begin, Date end, String comment) {
		beginTimes.add(begin);
		endTimes.add(end);
	}
	
	public Duration getTotalTime() {
		Duration duration = new Duration(0);
		for(int i = 0; i < beginTimes.getSize(); i++) {
			Date begin = beginTimes.get(i);
			Date end = endTimes.get(i);

			duration = duration.add(new Duration(end.getTime() - begin.getTime()));
		}
		return duration;
	}

	public ListProperty<Date> beginTimesProperty() {
		return beginTimes;
	}

	public ListProperty<String> commentsProperty() {
		return comments;
	}
	
	public ListProperty<Date> endTimesProperty() {
		return endTimes;
	}
	
	public Task getTask() {
		return task;
	}
}
