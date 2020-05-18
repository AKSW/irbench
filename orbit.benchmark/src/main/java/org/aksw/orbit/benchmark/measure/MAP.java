package org.aksw.orbit.benchmark.measure;

import java.util.ArrayList;
import java.util.List;

public class MAP extends AbstractMeasureK {

	public MAP(int k) {
		super(k);
	}

	@Override
	public double evaluate(List<String> benchmarkAnswerList, List<String> systemAnswerList, int k) {
		double map = 0;
		int systemMinK = Math.min(systemAnswerList.size(), k);
		List<String> systemAnswerSubList = new ArrayList<String>(systemAnswerList.subList(0, systemMinK));
		int m = 0;
		for(int i = 0; i < systemMinK; i++) {
			String answerAtI = systemAnswerSubList.get(i);
			if(benchmarkAnswerList.contains(answerAtI)) {
				m++;
				map += m / (double) (i+1);
			}
		}
		if(m == 0) {
			return 0;
		}
		return map / m;
	}
}
