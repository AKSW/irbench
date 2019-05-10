package org.aksw.orbit.benchmark.measure;

import java.util.ArrayList;
import java.util.List;

public class PrecisionAt extends AbstractMeasureK {
	
	public PrecisionAt(int k) {
		super(k);
	}

	@Override
	public double evaluate(List<String> benchmarkAnswerList, List<String> systemAnswerList, int k) {
		int lowerBenchK = Math.min(benchmarkAnswerList.size(), k); // the lower value between the benchmark answer size and k
		int lowerSysK = Math.min(systemAnswerList.size(), k);
		List<String> benchmarkAnswerSubList = new ArrayList<String>(benchmarkAnswerList.subList(0, lowerBenchK));
		List<String> systemAnswerSubList = new ArrayList<String>(systemAnswerList.subList(0, lowerSysK));
		systemAnswerSubList.retainAll(benchmarkAnswerSubList);
	    double intersectionSize = systemAnswerSubList.size();
	    double precision = intersectionSize / lowerBenchK;
	    return precision;
	}

}
