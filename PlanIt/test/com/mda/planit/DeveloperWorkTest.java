package com.mda.planit;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import com.mda.planit.model.DeveloperWork;

public class DeveloperWorkTest {

	@Test
	public void test() {
		Calendar begin = Calendar.getInstance(); 
		Calendar end = Calendar.getInstance(); 
		begin.set(2016, 11, 13, 17, 05, 0);
		end.set(2016, 11, 13, 18, 05, 0);		
		DeveloperWork work = new DeveloperWork("", begin.getTime(), end.getTime(), null);
		assertEquals(1, (int)work.getDuration().toHours());

		begin.set(2016, 11, 13, 1, 04, 0);
		end.set(2016, 11, 13, 17, 50, 0);
		work = new DeveloperWork("", begin.getTime(), end.getTime(), null);
		assertEquals(16, (int)work.getDuration().toHours());
		assertEquals(46, (int)(work.getDuration().toMinutes()) % 60);
		
		begin.set(2016, 11, 13, 21, 03, 0);
		end.set(2016, 11, 13, 21, 04, 0);
		work = new DeveloperWork("", begin.getTime(), end.getTime(), null);
		assertEquals(1, (int)(work.getDuration().toMinutes()) % 60);
	}

}
