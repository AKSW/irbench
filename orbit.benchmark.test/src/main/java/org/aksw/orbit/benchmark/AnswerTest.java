package org.aksw.orbit.benchmark;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aksw.orbit.benchmark.qald.schema.Answer;
import org.junit.Assert;
import org.junit.Test;

public class AnswerTest {
	
	@Test
	public void testAnswer() {
		Answer a = new Answer();
		a.setLiteral("a");
		
		Answer b = new Answer();
		b.setLiteral("a");
		
		Assert.assertTrue(a.equals(b));
	}
	
	@Test
	public void testAnswerList() {		
		List<Answer> listAnswerA = new ArrayList<Answer>();
		Answer a = new Answer();
		a.setDate("a");
		listAnswerA.add(a);
		
		List<Answer> listAnswerB = new ArrayList<Answer>();
		Answer b = new Answer();
		b.setLiteral("a");
		listAnswerB.add(b);
		
		Assert.assertTrue(listAnswerA.contains(b));
	}
	
	
	@Test
	public void testHashSet() {		
		Set<Answer> setAnswerA = new HashSet<Answer>();
		Answer a = new Answer();
		a.setNumber("a");
		setAnswerA.add(a);
		
		Set<Answer> setAnswerB = new HashSet<Answer>();
		Answer b = new Answer();
		b.setLiteral("a");
		setAnswerB.add(b);
		
		Assert.assertTrue(setAnswerA.contains(b));
		
		setAnswerA.removeAll(setAnswerB);
		
		Assert.assertEquals(0, setAnswerA.size());		
	}
	
}
