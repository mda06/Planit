package com.mda.planit.model.properties;

import com.mda.planit.model.SprintGoal;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;

public class GoalsPercProperty  {
	private final ListProperty<SprintGoal> goals;
	private final DoubleProperty value = new SimpleDoubleProperty() ;
    private final BooleanProperty valid = new SimpleBooleanProperty();
	
	public GoalsPercProperty(ListProperty<SprintGoal> goals) {
		this.goals = goals;
		goals.addListener(new ListChangeListener<SprintGoal>(){
			@Override
			public void onChanged(Change<? extends SprintGoal> c) {
				while (c.next()) {
					if(c.wasAdded()) {
						SprintGoal sg = goals.get(c.getFrom());
						sg.accomplishProperty().addListener(change -> calculatePerc());
					}
					
					calculatePerc();
				}
			}
	      });
		calculatePerc();
		setValid(true);
	}
	
	private void calculatePerc() {
		if(goals.isEmpty()) {
			setValue(100);
			return;
		}
		long accom = goals.stream().filter(goal -> goal.isAccomplish()).count();
		double v = (accom / (double) goals.size()) * 100;
		v *= 100;
		v = Math.round(v);
		v /= 100;
		setValue(v);
	}

    public final DoubleProperty valueProperty() {
        return value ;
    }

    public final double getValue() {
        return value.get();
    }

    public final void setValue(double value) {
        this.value.set(value);
    }

    public final BooleanProperty validProperty() {
        return valid ;
    }

    public final boolean isValid() {
        return valid.get();
    }

    public final void setValid(boolean valid) {
        this.valid.set(valid);
    }
}
