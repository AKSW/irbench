package org.aksw.orbit.benchmark;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class EncoderTest {
	
	@Test
	public void tabuarEncodeTest() throws Exception {
		Map<String, QuestionScoreMap> benchmark = new HashMap<>();

		QuestionScoreMap qScoreMap = new QuestionScoreMap();
		qScoreMap.put("Q1", 0.);
		qScoreMap.put("Q2", 1.);
		benchmark.put("m1", qScoreMap);

		QuestionScoreMap qScoreMap1 = new QuestionScoreMap();
		benchmark.put("m2", qScoreMap1);

		EvaluationEncoder encoder = new TabularEvaluationEncoder();
		String encoded = encoder.encode(benchmark);
		String expected = "|\tQ-ID\t|\tm1\t|\tm2\t|\r\n|\tQ1\t|\t0.0\t|\t?\t|\r\n|\tQ2\t|\t1.0\t|"
				+ "\t?\t|\r\n|\tAVG\t|\t0.5\t|\t0.0\t|";
		Assert.assertEquals(expected, encoded);
	}
	
	@Test
	public void latexEncodeTest() throws Exception {
		Map<String, QuestionScoreMap> benchmark = new HashMap<>();

		QuestionScoreMap qScoreMap = new QuestionScoreMap();
		qScoreMap.put("Q1", 0.);
		qScoreMap.put("Q2", 1.);
		benchmark.put("m1", qScoreMap);
		
		QuestionScoreMap qScoreMap1 = new QuestionScoreMap();
		benchmark.put("m2", qScoreMap1);
		EvaluationEncoder encoder = new LatexEvaluationEncoder();
		String encoded = encoder.encode(benchmark);
		String expected = "\\begin{center}\r\n\\begin{tabular}{| c | c | c |}"
				+ "\r\n\\hline\\hline\r\n\tQ\t&\tm1\t&\tm2\\\\\r\n\\hline\\hline\r\nQ1\t&\t0.0\t&\t?\t "
				+ "\\\\\r\nQ2\t&\t1.0\t&\t?\t \\\\\r\n\\hline\\hline\r\n\\end{tabular}\r\n\\end{center}";
		Assert.assertEquals(expected, encoded);

		qScoreMap1.put("Q1", 1.);
		qScoreMap1.put("Q2", 1.);

		encoded = encoder.encode(benchmark);
		expected = "\\begin{center}\r\n\\begin{tabular}{| c | c | c |}\r\n\\hline\\hline\r\n\tQ\t&"
				+ "\tm1\t&\tm2\\\\\r\n\\hline\\hline\r\nQ1\t&\t0.0\t&\t1.0\t \\\\\r\nQ2\t&\t1.0\t&\t1.0\t "
				+ "\\\\\r\n\\hline\\hline\r\n\\end{tabular}\r\n\\end{center}";
		Assert.assertEquals(expected, encoded);
	}
}
