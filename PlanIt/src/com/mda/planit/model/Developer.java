package com.mda.planit.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

public class Developer {
	private long id;
	private final StringProperty name;
	private final ObjectProperty<Color> color;
	
	public Developer(String name, Color c) {
		this(name, c, -1);
	}
	
	public Developer(String name, Color c, long id) {
		this.name = new SimpleStringProperty(name);
		this.color = new SimpleObjectProperty<Color>(c);
		this.id = id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public void setColor(Color color) {
		this.color.set(color);
	}
	
	public String getString() {
		return name.get();
	}
	
	public long getId() {
		return id;
	}
	
	public Color getColor() {
		return color.get();
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public ObjectProperty<Color> colorProperty() {
		return color;
	}
}
