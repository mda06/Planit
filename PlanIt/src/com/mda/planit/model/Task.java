package com.mda.planit.model;

import java.time.LocalDate;
import java.util.Date;

import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class Task {
	private long id;
	private final StringProperty name;
	private final StringProperty desc;
	private TaskState state;
	private final ObjectProperty<LocalDate> startDate;
	private final ObjectProperty<LocalDate> endDate;
	private final ListProperty<SprintGoal> goals;
	private final ListProperty<TaskLabel> labels;
	private final MapProperty<Developer, DeveloperTask> devTasks;
	
	public Task(String name, String desc, LocalDate start, LocalDate end) {
		this(name, desc, start, end, TaskState.TODO, -1);
	}
	
	public Task(String name, String desc, LocalDate start, LocalDate end, TaskState state, long id) {
		this.name = new SimpleStringProperty(name);
		this.desc = new SimpleStringProperty(desc);
		this.state = state;
		startDate = new SimpleObjectProperty<LocalDate>(start);
		endDate = new SimpleObjectProperty<LocalDate>(end);
		goals = new SimpleListProperty<SprintGoal>(FXCollections.observableArrayList());
		labels = new SimpleListProperty<TaskLabel>(FXCollections.observableArrayList());
		devTasks = new SimpleMapProperty<Developer, DeveloperTask>(FXCollections.observableHashMap());
		
		this.id = id;
	}
	
	public void addSprintGoal(SprintGoal sg) {
		if(sg == null) return;
		goals.add(sg);
	}
	
	public void addTaskLabel(TaskLabel lbl) {
		if(lbl == null) return;
		labels.add(lbl);
	}
	
	public void addDeveloperTask(Developer dev, Date begin, Date end, String comment) {
		if(dev == null || begin == null || end == null || comment == null) return;
		if(devTasks.get(dev) == null) {
			devTasks.put(dev, new DeveloperTask(this));
		}
		
		devTasks.get(dev).add(begin, end, comment);
	}
	
	public void removeSprintGoal(SprintGoal sg) {
		goals.remove(sg);
	}
	
	public void removeTaskLabel(TaskLabel lbl) {
		labels.remove(lbl);
	}
	
	public void removeDeveloperTask(Developer dev) {
		devTasks.remove(dev);
	}
	
	public void setTaskState(TaskState state) {
		this.state = state;
	}
	
	public void setStartDate(LocalDate date) {
		startDate.set(date);
	}
	
	public void setEndDate(LocalDate date) {
		endDate.set(date);
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public TaskState getState() {
		return state;
	}

	public LocalDate getStartDate() {
		return startDate.get();
	}
	
	public LocalDate getEndDate() {
		return endDate.get();
	}
	
	public String getName() {
		return name.get();
	}
	
	public String getDesc() {
		return desc.get();
	}
	
	public DeveloperTask getTasks(Developer dev) {
		return devTasks.get(dev);
	}
	
	public ListProperty<TaskLabel> getLabelList() {
		return labels;
	}
	
	public ListProperty<SprintGoal> getGoalsList() {
		return goals;
	}
	
	public MapProperty<Developer, DeveloperTask> getDeveloperTaskList() {
		return devTasks;
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
