package org.aksw.orbit.benchmark.measure;

import java.util.ArrayList;
import java.util.List;

public class PrecisionAt extends AbstractMeasureK {
	
	public PrecisionAt(int k) {
		super(k);
	}

	@Override
	public double evaluate(List<String> benchmarkAnswerList, List<String> systemAnswerList, int k) {
		double precision = 0.0;
		int lowerBenchK = Math.min(benchmarkAnswerList.size(), k); // the lower value between the benchmark answer size or k
		int lowerSysK = Math.min(systemAnswerList.size(), k);
		List<String> benchmarkAnswerSubList = new ArrayList<String>(benchmarkAnswerList.subList(0, lowerBenchK));
		List<String> systemAnswerSubList = systemAnswerList.subList(0, lowerSysK);
		benchmarkAnswerSubList.retainAll(systemAnswerSubList);
	    double intersectionSize = benchmarkAnswerSubList.size();
	    precision = intersectionSize / lowerBenchK;
	    return precision;
	}

}
