package com.mda.planit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Calendar;

import org.junit.Test;

import com.mda.planit.model.Developer;
import com.mda.planit.model.SprintGoal;
import com.mda.planit.model.Task;
import com.mda.planit.model.TaskLabel;

public class TaskTest {

	@Test
	public void testAddGoals() {
		Task t = new Task("", "", null, null);
		assertEquals(0, t.getGoalsList().getSize());
		t.addSprintGoal(null);
		assertEquals(0, t.getGoalsList().getSize());
		t.addSprintGoal(null);
		t.addSprintGoal(null);
		t.addSprintGoal(null);
		SprintGoal goal = new SprintGoal();
		t.addSprintGoal(goal);
		assertEquals(1, t.getGoalsList().getSize());
		assertSame(goal, t.getGoalsList().get(0));
	}
	
	@Test
	public void testAddLabels() {
		Task t = new Task("", "", null, null);
		assertEquals(0, t.getLabelList().getSize());
		t.addTaskLabel(null);
		assertEquals(0, t.getLabelList().getSize());
		TaskLabel tl = new TaskLabel();
		t.addTaskLabel(tl);
		t.addTaskLabel(null);
		t.addTaskLabel(null);
		assertEquals(1, t.getLabelList().size());
	}
	
	@Test
	public void testAddDeveloperTask() {
		Task t = new Task("", "", null, null);
		t.addDeveloperWork(null, null, null, "");
		//assertEquals(0, t.developersWorkProperty().getSize());
		
		Developer d1 = new Developer("Mike", null), d2 = new Developer("Toto", null);
		Calendar begin = Calendar.getInstance(); 
		Calendar end = Calendar.getInstance(); 
		begin.set(2016, 11, 13, 17, 05, 0);
		end.set(2016, 11, 13, 18, 05, 0);
		t.addDeveloperWork(d1, begin.getTime(), end.getTime(), "");
		begin.set(2016, 11, 13, 1, 04, 0);
		end.set(2016, 11, 13, 17, 50, 0);
		t.addDeveloperWork(d2, begin.getTime(), end.getTime(), "");
		//assertEquals(2, t.developersWorkProperty().getSize());
		
		begin.set(2016, 11, 13, 20, 04, 0);
		end.set(2016, 11, 13, 21, 04, 0);
		t.addDeveloperWork(d1, begin.getTime(), end.getTime(), "");
		//assertEquals(2, t.developersWorkProperty().getSize());
		
		/*Duration d = t.getTasks(d1).getTotalTime();
		assertEquals(2, (int)d.toHours());*/
	}

}
