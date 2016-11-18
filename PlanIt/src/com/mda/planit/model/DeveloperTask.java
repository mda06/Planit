package com.mda.planit.model;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.util.Duration;

public class DeveloperTask {
	private Task task;
	private ListProperty<DeveloperTaskDetail> details;
	
	public DeveloperTask(Task task) {
		this.task = task;
		details = new SimpleListProperty<DeveloperTaskDetail>(FXCollections.observableArrayList());
	}
	
	public void add(Date begin, Date end, String comment) {
		details.add(new DeveloperTaskDetail(comment, begin, end));
	}
	
	public Duration getTotalTime() {
		Duration duration = new Duration(0);
		for(int i = 0; i < details.getSize(); i++) {
			Date begin = details.get(i).getBeginDate();
			Date end = details.get(i).getEndDate();

			duration = duration.add(new Duration(end.getTime() - begin.getTime()));
		}
		return duration;
	}

	public List<Date> beginTimesProperty() {
		return details.stream().map(dt -> dt.getBeginDate()).collect(Collectors.toList());
	}

	public List<String> commentsProperty() {
		return details.stream().map(dt -> dt.getComment()).collect(Collectors.toList());
	}
	
	public List<Date> endTimesProperty() {
		return details.stream().map(dt -> dt.getEndDate()).collect(Collectors.toList());
	}
	
	public ListProperty<DeveloperTaskDetail> detailsProperty() {
		return details;
	}
	
	public Task getTask() {
		return task;
	}
}
