package org.aksw.orbit.benchmark.measure;

import java.util.ArrayList;
import java.util.List;

import org.aksw.orbit.benchmark.qald.schema.Answer;
import org.junit.Assert;
import org.junit.Test;

public class MAPTest {
	
	@Test
	public void testMap() {
		List<Answer> listAnswerA = new ArrayList<Answer>();
		Answer a = new Answer();
		a.setDate("a");
		listAnswerA.add(a);
		
		List<Answer> listAnswerB = new ArrayList<Answer>();
		Answer b = new Answer();
		b.setLiteral("a");
		listAnswerB.add(b);
		
		MAP map = new MAP(2);		
		double score = map.evaluateAnswers(listAnswerA, listAnswerB, 2);
		Assert.assertEquals(1., score, 0.);
		
		score = map.evaluateAnswers(listAnswerA, listAnswerB, 1);
		Assert.assertEquals(1., score, 0.);
		
		Answer a1 = new Answer();
		a1.setDate("b");
		listAnswerA.add(a1);
		
		score = map.evaluateAnswers(listAnswerA, listAnswerB, 2);
		Assert.assertEquals(1, score, 0.);
		
		score = map.evaluateAnswers(listAnswerA, listAnswerB, 1);
		Assert.assertEquals(1, score, 0.);
		
		Answer a2 = new Answer();
		a2.setDate("c");
		listAnswerA.add(a2);
		Answer b1 = new Answer();
		b1.setDate("c");
		listAnswerB.add(b1);
		
		score = map.evaluateAnswers(listAnswerA, listAnswerB, 2);
		Assert.assertEquals(1, score, 0.001);
		
		score = map.evaluateAnswers(listAnswerA, listAnswerB, 3);
		Assert.assertEquals(0.75, score, 0.0);
		
		score = map.evaluateAnswers(listAnswerA, listAnswerB, 1);
		Assert.assertEquals(1, score, 0.00);
		
		Answer b2 = new Answer();
		b2.setDate("e");
		listAnswerB.add(b2);
		
		Answer b3 = new Answer();
		b3.setDate("f");
		listAnswerB.add(b3);
		
		score = map.evaluateAnswers(listAnswerA, listAnswerB, 3);
		Assert.assertEquals(0.75, score, 0.0);
	}
}
