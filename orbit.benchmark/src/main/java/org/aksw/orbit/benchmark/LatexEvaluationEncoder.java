package org.aksw.orbit.benchmark;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

public class LatexEvaluationEncoder implements EvaluationEncoder {
	@Override
	public String encode(Map<String, QuestionScoreMap> benchmark) throws IOException {
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			try(PrintStream ps = new PrintStream(bos)) {		
				ps.println("\\begin{center}");
				ps.print("\\begin{tabular}{");
				int columnSize = benchmark.keySet().size() + 1;
				for(int i = 0; i < columnSize; i++) {
					ps.print("| c ");
				}
				ps.println("|}");
				ps.println("\\hline\\hline");
				ps.print("\tQ");
				for(String measure : benchmark.keySet()) {
					ps.print("\t&\t" + measure);
				}
				ps.println("\\\\");
				ps.println("\\hline\\hline");
				QuestionScoreMap mainScoreMap = null;
				for(String measure : benchmark.keySet()) {
					mainScoreMap = benchmark.get(measure);
					break;
				}
				if(mainScoreMap != null) {
					for (String question : mainScoreMap.keySet()) {
						ps.print(question);
						for(String measure : benchmark.keySet()) {
							QuestionScoreMap scoreMap = benchmark.get(measure);
							if(scoreMap != null) {
								Double value = scoreMap.get(question);
								if(value != null) {
									ps.print("\t&\t" + value);
								} else {
									ps.print("\t&\t?");
								}
							} else {
								ps.print("\t&\t?");
							}
						}
						ps.println("\t \\\\");
					}
				}
				ps.println("\\hline\\hline");
				ps.println("\\end{tabular}");
				ps.print("\\end{center}");
				return new String(bos.toByteArray());
			}
		}
	}

}
