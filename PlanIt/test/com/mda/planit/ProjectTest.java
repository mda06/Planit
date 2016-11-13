package com.mda.planit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mda.planit.model.Developer;
import com.mda.planit.model.Project;
import com.mda.planit.model.Sprint;

public class ProjectTest {

	@Test
	public void testAddDevs() {
		Project p = new Project("", "", null, null, -1);
		assertEquals(0, p.devsProperty().getSize());
		p.addDeveloper(new Developer("", null));
		Developer d =new Developer("", null);
		p.addDeveloper(d);
		p.addDeveloper(null);
		p.addDeveloper(new Developer("", null));
		assertEquals(3, p.devsProperty().getSize());
		p.removeDeveloper(d);
		assertEquals(2, p.devsProperty().getSize());
	}

	@Test
	public void testAddSprints() {
		Project p = new Project("", "", null, null, -1);
		assertEquals(0, p.sprintsProperty().getSize());
		p.addSprint(null);
		p.addSprint(new Sprint("", null, null));
		p.addSprint(new Sprint("", null, null));
		Sprint s = new Sprint("", null, null);
		p.addSprint(s);
		assertEquals(3, p.sprintsProperty().getSize());
		p.removeSprint(s);
		assertEquals(2, p.sprintsProperty().getSize());
	}
}
