package com.mda.planit.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

public class DeveloperWork {
	private final StringProperty comment;
	private final ObjectProperty<Duration> duration;
	private final ObjectProperty<Date> beginDate;
	private final ObjectProperty<Date> endDate;
	private final ObjectProperty<Task> toTask;	
	
	public DeveloperWork() {
		this("", null, null, null);
	}
	
	public DeveloperWork(String comment, Date begin, Date end, Task task) {
		this.comment = new SimpleStringProperty(comment);
		beginDate = new SimpleObjectProperty<>(begin);
		endDate = new SimpleObjectProperty<>(end);
		toTask = new SimpleObjectProperty<>(task);
		duration = new SimpleObjectProperty<>(new Duration(end.getTime() - begin.getTime()));
	}
	
	public String getComment() {
		return comment.get();
	}
	
	public Date getBeginDate() {
		return beginDate.get();
	}
	
	public Date getEndDate() {
		return endDate.get();
	}
	
	public Duration getDuration() {
		return duration.get();
	}
	
	public Task getTask() {
		return toTask.get();
	}
	
	public ObjectProperty<Task> taskProperty() {
		return toTask;
	}
	
	public ObjectProperty<Duration> durationProperty() {
		return duration;
	}
	
	public SimpleStringProperty beginDateStringProperty() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new SimpleStringProperty(df.format(beginDate.get()));
	}
	
	public SimpleStringProperty endDateStringProperty() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new SimpleStringProperty(df.format(endDate.get()));
	}
	
	public StringProperty commentProperty() {
		return comment;
	}
	
	public ObjectProperty<Date> beginDateProperty() {
		return beginDate;
	}
	
	public ObjectProperty<Date> endDateProperty() {
		return endDate;
	}
}
