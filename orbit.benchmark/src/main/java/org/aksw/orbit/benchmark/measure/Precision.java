package org.aksw.orbit.benchmark.measure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Precision extends AbstractMeasure {
	@Override
	public double evaluate(List<String> benchmarkAnswerList, List<String> systemAnswerList) {
		Set<String> givenAnswerSet = new HashSet<String>(systemAnswerList);
		double givenAnswerSetSize = givenAnswerSet.size();
		if(givenAnswerSetSize == 0) {
			return 0; // score value is 0
		}	
		Set<String> intersectionAnswerSet = new HashSet<String>(benchmarkAnswerList);
		intersectionAnswerSet.retainAll(givenAnswerSet); // calculating the intersection
		double precision = intersectionAnswerSet.size() / givenAnswerSetSize;
		return precision;
	}
}
