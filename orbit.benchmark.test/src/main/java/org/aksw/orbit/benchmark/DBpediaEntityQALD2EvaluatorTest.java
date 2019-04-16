package org.aksw.orbit.benchmark;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.aksw.orbit.benchmark.measure.MAP;
import org.aksw.orbit.benchmark.measure.Measure;
import org.aksw.orbit.benchmark.measure.PrecisionAt;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.dataset.parser.DatasetParser;
import org.aksw.orbit.dataset.parser.DatasetParserFactory;
import org.junit.Assert;
import org.junit.Test;

public class DBpediaEntityQALD2EvaluatorTest {
	
	@Test
	public void evaluationTest() throws Exception {
		URL benchmarkAnswers = PrecisionAt.class.getResource("qrels-v2.a.trec");
		 URL systemAnswers = PrecisionAt.class.getResource("qrels-v2.a.trec");		 
		 DatasetParserFactory factory = new DatasetParserFactory();
		 DatasetParser parser = factory.getDatasetParser(benchmarkAnswers);
		 Dataset benchmarkDataset = parser.parse(benchmarkAnswers);
		 Dataset fsdmAnswer = parser.parse(systemAnswers);
		 Map<String, Measure> measures = new HashMap<String, Measure>();
		 measures.put("MAP@10", new MAP(10));
		 Evaluator evaluator = new DBpediaEntityQALD2Evaluator(fsdmAnswer, measures);
		 benchmarkDataset.accept(evaluator);
		 Assert.assertEquals(140,evaluator.getEvaluation().get("MAP@10").size());
		 Assert.assertEquals(1.,evaluator.getEvaluation().get("MAP@10").getAVG(),0);
	}
	
	@Test
	public void evaluationTest2() throws Exception {
		URL benchmarkAnswers = PrecisionAt.class.getResource("qrels-v2.a.trec");
		 URL systemAnswers = PrecisionAt.class.getResource("fsdm-elr_v2.run.a.trec");		 
		 DatasetParserFactory factory = new DatasetParserFactory();
		 DatasetParser parser = factory.getDatasetParser(benchmarkAnswers);
		 Dataset benchmarkDataset = parser.parse(benchmarkAnswers);
		 Dataset fsdmAnswer = parser.parse(systemAnswers);
		 Map<String, Measure> measures = new HashMap<String, Measure>();
		 measures.put("MAP@10", new MAP(10));
		 Evaluator evaluator = new DBpediaEntityQALD2Evaluator(fsdmAnswer, measures);
		 benchmarkDataset.accept(evaluator);
		 Assert.assertEquals(140,evaluator.getEvaluation().get("MAP@10").size());
		 Assert.assertEquals(0.07,evaluator.getEvaluation().get("MAP@10").getAVG(),0.002);
	}
}
