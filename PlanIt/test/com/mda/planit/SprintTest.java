package com.mda.planit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mda.planit.model.Sprint;
import com.mda.planit.model.SprintGoal;
import com.mda.planit.model.Task;

public class SprintTest {

	@Test
	public void testAddGoals() {
		Sprint sp = new Sprint("", null, null);
		sp.addSprintGoal(null);
		assertEquals(0, sp.goalsProperty().getSize());
		
		sp.addSprintGoal(new SprintGoal());
		sp.addSprintGoal(new SprintGoal());
		sp.addSprintGoal(new SprintGoal());
		assertEquals(3, sp.goalsProperty().getSize());
	}
	
	@Test
	public void testAddTasks() {
		Sprint sp = new Sprint("", null, null);
		sp.addTask(null);
		assertEquals(0, sp.taskProperty().getSize());
		
		sp.addTask(new Task("", "", null, null));
		assertEquals(1, sp.taskProperty().getSize());

		sp.addTask(new Task("", "", null, null));
		sp.addTask(new Task("", "", null, null));
		sp.addTask(null);
		assertEquals(3, sp.taskProperty().getSize());
	}

}
