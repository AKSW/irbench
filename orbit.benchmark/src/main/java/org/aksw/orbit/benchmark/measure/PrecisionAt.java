package org.aksw.orbit.benchmark.measure;

import java.util.List;

public class PrecisionAt extends AbstractMeasureK {
	
	public PrecisionAt(int k) {
		super(k);
	}

	@Override
	public double evaluate(List<String> benchmarkAnswerList, List<String> systemAnswerList, int k) {
		int benchmarkSize = benchmarkAnswerList.size();
		int lowerBenchK = Math.min(benchmarkSize, k); // the lower value between the benchmark answer size and k
		int lowerSysK = Math.min(systemAnswerList.size(), k);
		int intersectionSize = 0;
		for(int i = 0; i < lowerSysK ; i++) {
			if(benchmarkAnswerList.contains(systemAnswerList.get(i))) {
				intersectionSize++;
			}
		}
	    double precision = intersectionSize / (double) lowerBenchK;
	    return precision;
	}

}
