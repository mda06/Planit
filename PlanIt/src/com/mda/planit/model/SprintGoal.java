package com.mda.planit.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SprintGoal {
	private long id;
	private final StringProperty name, desc;
	private final BooleanProperty accomplish;

	public SprintGoal() {
		this("", "");
	}

	public SprintGoal(String name, String desc) {
		this(name, desc, false, -1);
	}
	
	public SprintGoal(String name, String desc, boolean accomplish, long id) {
		this.name = new SimpleStringProperty(name);
		this.desc = new SimpleStringProperty(desc);
		this.accomplish = new SimpleBooleanProperty(accomplish);
		this.id = id;
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
	
	public void setAccomplish(boolean b) {
		this.accomplish.set(b);
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
	
	public boolean isAccomplish() {
		return accomplish.get();
	}

	public BooleanProperty accomplishProperty() {
		return accomplish;
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
