package com.mda.planit;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.mda.planit.model.DeveloperTask;

import javafx.util.Duration;

public class DeveloperTaskTest {
	private DeveloperTask dt = new DeveloperTask(null);

	@Test
	public void testGetTotalTime() {
		Calendar begin = Calendar.getInstance(); 
		Calendar end = Calendar.getInstance(); 
		begin.set(2016, 11, 13, 17, 04, 0);
		end.set(2016, 11, 13, 17, 05, 0);
		addTime(begin.getTime(), end.getTime());
		testTime(0, 1, 0);
		
		begin.set(2016, 11, 13, 17, 10, 50);
		end.set(2016, 11, 13, 17, 11, 12);
		addTime(begin.getTime(), end.getTime());
		testTime(22, 1, 0);
		
		begin.set(2016, 11, 13, 18, 00, 0);
		end.set(2016, 11, 13, 19, 00, 0);
		addTime(begin.getTime(), end.getTime());
		testTime(22, 1, 1);
		
		begin.set(2016, 11, 13, 22, 05, 17);
		end.set(2016, 11, 14, 00, 01, 22);
		addTime(begin.getTime(), end.getTime());
		testTime(27, 57, 2);
		
		begin.set(2016, 11, 13, 00, 00, 00);
		end.set(2016, 12, 13, 00, 00, 00);
		addTime(begin.getTime(), end.getTime());
		testTime(27, 57, 746);
	}
	
	public void addTime(Date begin, Date end) {
		dt.add(begin, end, "");
	}

	public void testTime(int s, int m, int h) {
		Duration d = dt.getTotalTime();
		int sec = (int)d.toSeconds() % 60;
		int min = (int)d.toMinutes() % 60;
		int hours = (int)d.toHours();
		assertEquals(s, sec);
		assertEquals(m, min);
		assertEquals(h, hours);
	}
}
