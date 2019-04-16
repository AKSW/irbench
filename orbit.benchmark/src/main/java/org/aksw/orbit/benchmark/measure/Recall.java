package org.aksw.orbit.benchmark.measure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Recall extends AbstractMeasure {

	@Override
	public double evaluate(List<String> benchmarkAnswerList, List<String> systemAnswerList) {
		double precision = 0;
		if(benchmarkAnswerList.size() == 0) {
			return precision;
		}
		Set<String> intersectionAnswerSet = new HashSet<String>(benchmarkAnswerList);
		Set<String> actualAnswerSet = new HashSet<String>(systemAnswerList);
		intersectionAnswerSet.retainAll(actualAnswerSet); // calculating the intersection
	
		precision = (double) intersectionAnswerSet.size() / (double) benchmarkAnswerList.size();
		
		return precision;
	}

}
