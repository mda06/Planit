package com.mda.planit.model.properties;

import com.mda.planit.model.Task;
import com.mda.planit.model.TaskState;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;

public class TasksPercProperty {
	private final ListProperty<Task> tasks;
	private final DoubleProperty value = new SimpleDoubleProperty();
	private final BooleanProperty valid = new SimpleBooleanProperty();

	public TasksPercProperty(ListProperty<Task> goals) {
		this.tasks = goals;
		tasks.addListener(new ListChangeListener<Task>() {
			@Override
			public void onChanged(Change<? extends Task> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						Task t = tasks.get(c.getFrom());
						t.taskStateProperty().addListener(change -> calculatePerc());
					}

					calculatePerc();
				}
			}
		});
		calculatePerc();
		setValid(true);
	}

	private void calculatePerc() {
		if (tasks.isEmpty()) {
			setValue(100);
			return;
		}
		long accom = tasks.stream().filter(task -> task.getState() == TaskState.DONE).count();
		double v = (accom / (double) tasks.size()) * 100;
		v *= 100;
		v = Math.round(v);
		v /= 100;
		setValue(v);
	}

	public final DoubleProperty valueProperty() {
		return value;
	}

	public final double getValue() {
		return value.get();
	}

	public final void setValue(double value) {
		this.value.set(value);
	}

	public final BooleanProperty validProperty() {
		return valid;
	}

	public final boolean isValid() {
		return valid.get();
	}

	public final void setValid(boolean valid) {
		this.valid.set(valid);
	}
}
