package org.aksw.orbit.benchmark;

import java.util.HashMap;
import java.util.Map;

import org.aksw.orbit.benchmark.measure.Measure;
import org.aksw.orbit.benchmark.qald.schema.Dataset;

public class Benchmark {	
	public static Map<String, QuestionScoreMap> evaluate(Dataset benchmark, 
			Dataset system,
			Map<String, Measure> measures) {
		Map<String, QuestionScoreMap> scoreMap = new HashMap<String, QuestionScoreMap>();
		for(String measureID: measures.keySet()) {			
			Measure measure = measures.get(measureID);
			QuestionScoreMap score = measure.evaluate(benchmark, system);
			scoreMap.put(measureID, score);
		}
		return scoreMap;
	}
}
