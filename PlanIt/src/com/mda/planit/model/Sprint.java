package com.mda.planit.model;

import java.time.LocalDate;

import com.mda.planit.model.properties.GoalsPercProperty;
import com.mda.planit.model.properties.TasksPercProperty;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sprint {
	private long id;
	private final StringProperty name;
	private final ObjectProperty<LocalDate> startDate;
	private final ObjectProperty<LocalDate> endDate;
	private final ListProperty<SprintGoal> goals;
	private final GoalsPercProperty goalsPerc;
	private final ListProperty<Task> tasks;
	private final TasksPercProperty tasksPerc;

	public Sprint() {
		this("", null, null);
	}

	public Sprint(String name, LocalDate start, LocalDate end) {
		this(name, start, end, -1);
	}

	public Sprint(String name, LocalDate start, LocalDate end, long id) {
		this.name = new SimpleStringProperty(name);
		startDate = new SimpleObjectProperty<LocalDate>(start);
		endDate = new SimpleObjectProperty<LocalDate>(end);
		goals = new SimpleListProperty<SprintGoal>(FXCollections.observableArrayList());
		goalsPerc = new GoalsPercProperty(goals);
		tasks = new SimpleListProperty<Task>(FXCollections.observableArrayList());
		tasksPerc = new TasksPercProperty(tasks);

		this.id = id;
	}

	public void addSprintGoal(SprintGoal sg) {
		if (sg == null) return;
		goals.add(sg);
	}

	public void addTask(Task t) {
		if (t == null) return;
		tasks.add(t);
	}

	public void removeSprintGoal(SprintGoal g) {
		goals.remove(g);
	}

	public void removeTask(Task t) {
		tasks.remove(t);
	}

	public void setId(long l) {
		id = l;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public void setStartDate(LocalDate d) {
		startDate.set(d);
	}

	public void setEndDate(LocalDate d) {
		endDate.set(d);
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name.get();
	}

	public LocalDate getStartDate() {
		return startDate.get();
	}

	public LocalDate getEndDate() {
		return endDate.get();
	}

	/*
	 * public List<DeveloperTask> getTasks(Developer d) { List<DeveloperTask>
	 * lst = new ArrayList<DeveloperTask>();
	 * 
	 * for(Task s : tasks) { DeveloperTask dt = s.getTasks(d); if(dt != null)
	 * lst.add(dt); }
	 * 
	 * return lst; }
	 */

	public ObservableList<DeveloperWork> developerAllWorkProperty(Developer d) {
		ReadOnlyListWrapper<DeveloperWork> lst = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

		for (Task t : taskProperty()) {
			lst.addAll(t.workOfProperty(d));
		}
		return FXCollections.unmodifiableObservableList(lst);
	}

	public DoubleProperty goalsPercProperty() {
		return goalsPerc.valueProperty();
	}

	public DoubleProperty tasksPercProperty() {
		return tasksPerc.valueProperty();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public ObjectProperty<LocalDate> startDateProperty() {
		return startDate;
	}

	public ObjectProperty<LocalDate> endDateProperty() {
		return endDate;
	}

	public ListProperty<SprintGoal> goalsProperty() {
		return goals;
	}

	public ListProperty<Task> taskProperty() {
		return tasks;
	}
}
