package com.mda.planit.model;

import java.time.LocalDate;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class Project {
	private long id;
	private final StringProperty name;
	private final StringProperty desc;
	private final ObjectProperty<LocalDate> startDate;
	private final ObjectProperty<LocalDate> endDate;
	private final ListProperty<Developer> devs;
	private final ListProperty<Sprint> sprints;
	
	public Project(String name, String desc, LocalDate start, LocalDate end) {
		this(name, desc, start, end, -1);
	}
	
	public Project(String name, String desc, LocalDate start, LocalDate end, long id) {
		this.name = new SimpleStringProperty(name);
		this.desc = new SimpleStringProperty(desc);
		startDate = new SimpleObjectProperty<LocalDate>(start);
		endDate = new SimpleObjectProperty<LocalDate>(end);
		devs = new SimpleListProperty<Developer>(FXCollections.observableArrayList());
		sprints = new SimpleListProperty<Sprint>(FXCollections.observableArrayList());
		
		this.id = id;
	}
	
	public void addSprint(Sprint s) {
		if(s == null) return;
		sprints.add(s);
	}
	
	public void addDeveloper(Developer d) {
		if(d == null) return;
		devs.add(d);
	}
	
	public void removeDeveloper(Developer d) {
		devs.remove(d);
	}
	
	public void removeSprint(Sprint s) {
		sprints.remove(s);
	}

	public void setStartDate(LocalDate date) {
		startDate.set(date);
	}
	
	public void setEndDate(LocalDate date) {
		endDate.set(date);
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public void setDesc(String desc) {
		this.desc.set(desc);
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getDesc() {
		return desc.get();
	}

	public LocalDate getStartDate() {
		return startDate.get();
	}
	
	public LocalDate getEndDate() {
		return endDate.get();
	}
	
	public ListProperty<DeveloperTask> getTasks(Developer d) {
		ListProperty<DeveloperTask> lst = new SimpleListProperty<DeveloperTask>();

		for(Sprint s : sprints) {
			for(DeveloperTask dt : s.getTasks(d)) {
				lst.add(dt);
			}
		}
		
		return lst;
	}
	
	public ListProperty<Sprint> sprintsProperty() {
		return sprints;
	}
	
	public ListProperty<Developer> devsProperty() {
		return devs;
	}
	
	public ObjectProperty<LocalDate> startDateProperty() {
		return startDate;
	}
	
	public ObjectProperty<LocalDate> endDateProperty() {
		return endDate;
	}
	
	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty descProperty() {
		return desc;
	}
}
