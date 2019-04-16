package org.aksw.orbit.benchmark.measure;

import java.util.List;

import org.aksw.orbit.benchmark.qald.schema.Answer;

public abstract class AbstractMeasureK extends AbstractMeasure {

	private int k = 0;
	
	public AbstractMeasureK(int k) {
		this.k = k;
	}
	
	public double evaluateAnswers(List<Answer> benchmarkAnswerList, List<Answer> systemAnswerList, int k) {
		List<String> benchmarkList = answerList2StringList(benchmarkAnswerList);
		List<String> systemList = answerList2StringList(systemAnswerList);
		return evaluate(benchmarkList, systemList, k);
	}
	
	@Override
	public double evaluate(List<String> benchmarkAnswerList, List<String> systemAnswerList) {		
		return evaluate(benchmarkAnswerList, systemAnswerList, k);
	}
	
	public abstract double evaluate(List<String> benchmarkAnswerList, List<String> systemAnswerList, int k);

}
