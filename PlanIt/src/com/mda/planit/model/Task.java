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
import javafx.util.Duration;

public class Task {
	private long id;
	private final StringProperty name;
	private final StringProperty desc;
	private final ObjectProperty<TaskState> state;
	private final ObjectProperty<LocalDate> startDate;
	private final ObjectProperty<LocalDate> endDate;
	private final ListProperty<SprintGoal> goals;
	private final ListProperty<TaskLabel> labels;
	private final ListProperty<Developer> assignedDevs;
	private final MapProperty<Developer, ListProperty<DeveloperWork>> devWork;
	private final MapProperty<Developer, ObjectProperty<Duration>> devWorkTime;

	public Task() {
		this("", "", null, null);
	}

	public Task(String name, String desc, LocalDate start, LocalDate end) {
		this(name, desc, start, end, TaskState.TODO, -1);
	}

	public Task(String name, String desc, LocalDate start, LocalDate end, TaskState state, long id) {
		this.name = new SimpleStringProperty(name);
		this.desc = new SimpleStringProperty(desc);
		this.state = new SimpleObjectProperty<>(state);
		startDate = new SimpleObjectProperty<LocalDate>(start);
		endDate = new SimpleObjectProperty<LocalDate>(end);
		goals = new SimpleListProperty<SprintGoal>(FXCollections.observableArrayList());
		assignedDevs = new SimpleListProperty<Developer>(FXCollections.observableArrayList());
		labels = new SimpleListProperty<TaskLabel>(FXCollections.observableArrayList());
		devWork = new SimpleMapProperty<Developer, ListProperty<DeveloperWork>>(FXCollections.observableHashMap());
		devWorkTime = new SimpleMapProperty<>(FXCollections.observableHashMap());

		this.id = id;
	}

	public void addSprintGoal(SprintGoal sg) {
		if (sg == null) return;
		goals.add(sg);
	}

	public void addTaskLabel(TaskLabel lbl) {
		if (lbl == null) return;
		labels.add(lbl);
	}

	public void addDev(Developer d) {
		if (d == null) return;
		assignedDevs.add(d);
	}

	public void addDeveloperWork(Developer dev, Date begin, Date end, String comment) {
		if (dev == null || begin == null || end == null || comment == null) return;
		if (devWork.get(dev) == null) {
			devWork.put(dev, new SimpleListProperty<>(FXCollections.observableArrayList()));
			devWorkTime.put(dev, new SimpleObjectProperty<Duration>(new Duration(0)));
		}

		DeveloperWork dw = new DeveloperWork(comment, begin, end, this);
		devWork.get(dev).add(dw);
		devWorkTime.get(dev).set(devWorkTime.get(dev).get().add(dw.getDuration()));
	}

	public void removeSprintGoal(SprintGoal sg) {
		goals.remove(sg);
	}

	public void removeDev(Developer d) {
		assignedDevs.remove(d);
	}

	public void removeTaskLabel(TaskLabel lbl) {
		labels.remove(lbl);
	}

	public void removeDeveloperTask(Developer dev) {
		devWork.remove(dev);
		devWorkTime.remove(dev);
	}

	public void setTaskState(TaskState state) {
		this.state.set(state);
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
		return state.get();
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

	public ListProperty<DeveloperWork> workOfProperty(Developer dev) {
		if (devWork.get(dev) == null) {
			devWork.put(dev, new SimpleListProperty<>(FXCollections.observableArrayList()));
			devWorkTime.put(dev, new SimpleObjectProperty<Duration>(new Duration(0)));
		}
		return devWork.get(dev);
	}

	public ObjectProperty<Duration> getDurationOf(Developer dev) {
		return devWorkTime.get(dev);
	}

	public ListProperty<TaskLabel> getLabelList() {
		return labels;
	}

	public ListProperty<SprintGoal> getGoalsList() {
		return goals;
	}

	public ListProperty<Developer> getAssignedDevsList() {
		return assignedDevs;
	}

	public ObjectProperty<LocalDate> startDateProperty() {
		return startDate;
	}

	public ObjectProperty<LocalDate> endDateProperty() {
		return endDate;
	}

	public ObjectProperty<TaskState> taskStateProperty() {
		return state;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty descProperty() {
		return desc;
	}

	@Override
	public String toString() {
		return name.get();
	}
}
