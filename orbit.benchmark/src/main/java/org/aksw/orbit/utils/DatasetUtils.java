package org.aksw.orbit.utils;

import java.util.List;

import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.Question;

public class DatasetUtils {
	public static Question getQuestion(String id, Dataset dataset) {
		return getQuestion(id, dataset.getQuestion());
	}
	
	public static Question getQuestion(String id, List<Question> questions) {
		for(Question question : questions) {
			if(question.getId().equals(id)) {
				return question;
			}
		}
		return null;
	}
}
