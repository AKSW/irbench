package org.aksw.orbit.benchmark.measure;

import java.util.List;

public class FMeasure extends AbstractMeasure {

	@Override
	public double evaluate(List<String> benchmarkAnswerList, List<String> systemAnswerList) {
		Precision precision = new Precision();
		Recall recall = new Recall();
		
		double precisionScore = precision.evaluate(benchmarkAnswerList, systemAnswerList);
		double recallScore = recall.evaluate(benchmarkAnswerList, systemAnswerList);
		
		double fMeasure = 0;
		if ((precisionScore + recallScore) > 0) { // avoid division by 0
			fMeasure = (2 * precisionScore * recallScore) / (precisionScore + recallScore);
		}
		return fMeasure;
	}

}
