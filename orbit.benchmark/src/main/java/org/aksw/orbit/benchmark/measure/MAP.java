package org.aksw.orbit.benchmark.measure;

import java.util.ArrayList;
import java.util.List;

public class MAP extends PrecisionAt {

	public MAP(int k) {
		super(k);
	}

	@Override
	public double evaluate(List<String> benchmarkAnswerList, List<String> systemAnswerList, int k) {
		double map = 0;
		int minK = Math.min(benchmarkAnswerList.size(), k);
		int systemMinK = Math.min(systemAnswerList.size(), minK);
		List<String> benchmarkAnswerSubList = new ArrayList<>(benchmarkAnswerList.subList(0, minK));
		List<String> systemAnswerSubList = systemAnswerList.subList(0, systemMinK);

		for(int i = 0; i < minK; i++) {
			String answerAtI = null;
			if(i < systemAnswerSubList.size()) {
				answerAtI = systemAnswerSubList.get(i);
			}
			if(benchmarkAnswerSubList.contains(answerAtI)) {
				map += super.evaluate(benchmarkAnswerSubList, systemAnswerSubList, i+1);
			}
		}
		if(map == 0) {
			return 0;
		}

		benchmarkAnswerSubList.retainAll(systemAnswerSubList);
		int intersectionSize = benchmarkAnswerSubList.size();
		return map / intersectionSize;
	}
}
