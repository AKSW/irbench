package org.aksw.orbit.benchmark;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

public class TabularEvaluationEncoder implements EvaluationEncoder {
	@Override
	public String encode(Map<String, QuestionScoreMap> benchmark) throws IOException {
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			PrintStream ps = new PrintStream(bos);		
			ps.print("|\tQ-ID");
			for(String measure : benchmark.keySet()) {
				ps.print("\t|\t" + measure);
			}
			ps.println("\t|");
			QuestionScoreMap mainScoreMap = null;
			for(String measure : benchmark.keySet()) {
				mainScoreMap = benchmark.get(measure);
				break;
			}
			if(mainScoreMap != null) {
				for (String question : mainScoreMap.keySet()) {
					ps.print("|\t" + question);
					for(String measure : benchmark.keySet()) {
						QuestionScoreMap scoreMap = benchmark.get(measure);
						if(scoreMap != null) {
							Double value = scoreMap.get(question);
							if(value != null) {
								ps.print("\t|\t" + value);
							} else {
								ps.print("\t|\t?");
							}
						} else {
							ps.print("\t|\t?");
						}
					}
					ps.println("\t|");
				}
				ps.print("|\tAVG");
				for(String measure : benchmark.keySet()) {
					QuestionScoreMap scoreMap = benchmark.get(measure);
					ps.print("\t|\t" + scoreMap.getAVG());
				}
				ps.print("\t|");
			}		
			return new String(bos.toByteArray());
		}
	}

}
