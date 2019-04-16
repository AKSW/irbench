package org.aksw.orbit.benchmark.measure;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aksw.orbit.benchmark.Evaluator;
import org.aksw.orbit.benchmark.qald.schema.Answer;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.dataset.parser.DatasetParser;
import org.aksw.orbit.dataset.parser.DatasetParserFactory;
import org.junit.Assert;
import org.junit.Test;

public class PrecisionAtTest {
	
	@Test
	public void testPrecision() {
		List<Answer> listAnswerA = new ArrayList<Answer>();
		Answer a = new Answer();
		a.setDate("a");
		listAnswerA.add(a);
		
		List<Answer> listAnswerB = new ArrayList<Answer>();
		Answer b = new Answer();
		b.setLiteral("a");
		listAnswerB.add(b);
		
		PrecisionAt p = new PrecisionAt(2);
		double score = p.evaluateAnswers(listAnswerA, listAnswerB, 2);
		Assert.assertEquals(1., score, 0.);
		
		score = p.evaluateAnswers(listAnswerA, listAnswerB, 1);
		Assert.assertEquals(1., score, 0.);
		
		Answer a1 = new Answer();
		a1.setDate("b");
		listAnswerA.add(a1);
		
		score = p.evaluateAnswers(listAnswerA, listAnswerB, 2);
		Assert.assertEquals(0.5, score, 0.);
		
		score = p.evaluateAnswers(listAnswerA, listAnswerB, 1);
		Assert.assertEquals(1, score, 0.);
		
		Answer a2 = new Answer();
		a2.setDate("c");
		listAnswerA.add(a2);
		Answer b1 = new Answer();
		b1.setDate("c");
		listAnswerB.add(b1);
		
		score = p.evaluateAnswers(listAnswerA, listAnswerB, 2);
		Assert.assertEquals(0.5, score, 0.0);
		
		score = p.evaluateAnswers(listAnswerA, listAnswerB, 3);
		Assert.assertEquals(0.66, score, 0.01);
		
		score = p.evaluateAnswers(listAnswerA, listAnswerB, 1);
		Assert.assertEquals(1, score, 0.00);
		
		Answer b2 = new Answer();
		b2.setDate("e");
		listAnswerB.add(b2);
		
		Answer b3 = new Answer();
		b3.setDate("f");
		listAnswerB.add(b3);
		
		score = p.evaluateAnswers(listAnswerA, listAnswerB, 3);
		Assert.assertEquals(0.66, score, .01);
		
		score = p.evaluateAnswers(listAnswerA, listAnswerB, 4);
		Assert.assertEquals(0.66, score, .01);
		
		score = p.evaluateAnswers(listAnswerA, listAnswerB, 10);
		Assert.assertEquals(0.66, score, .01);
	}
	
	@Test
	public void testPrecisionFromFile() throws Exception {
		 URL benchmarkAnswers = PrecisionAt.class.getResource("qrels-v2.a.trec");
		 URL systemAnswers = PrecisionAt.class.getResource("qrels-v2.a.trec");		 
		 DatasetParserFactory factory = new DatasetParserFactory();
		 DatasetParser parser = factory.getDatasetParser(benchmarkAnswers);
		 Dataset benchmarkDataset = parser.parse(benchmarkAnswers);
		 Dataset fsdmAnswer = parser.parse(systemAnswers);
		 Map<String, Measure> measures = new HashMap<String, Measure>();
		 measures.put("MAP@10", new MAP(10));
		 Evaluator evaluator = new Evaluator(fsdmAnswer, measures);
		 benchmarkDataset.accept(evaluator);		 
		 Assert.assertEquals(1.,evaluator.getEvaluation().get("MAP@10").getAVG(),0);
	}
}
