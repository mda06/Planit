package com.mda.planit.model;

import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DeveloperTaskDetail {
	private final StringProperty comment;
	private final ObjectProperty<Date> beginDate;
	private final ObjectProperty<Date> endDate;
	
	public DeveloperTaskDetail() {
		this("", null, null);
	}
	
	public DeveloperTaskDetail(String comment, Date begin, Date end) {
		this.comment = new SimpleStringProperty(comment);
		beginDate = new SimpleObjectProperty<>(begin);
		endDate = new SimpleObjectProperty<>(end);
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
