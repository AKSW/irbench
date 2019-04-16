package org.aksw.orbit.benchmark.measure;

import java.util.ArrayList;
import java.util.List;

import org.aksw.orbit.benchmark.QuestionScoreMap;
import org.aksw.orbit.benchmark.qald.schema.Answer;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.Question;

public abstract class AbstractMeasure implements Measure {
	@Override
	public QuestionScoreMap evaluate(Dataset benchmark, Dataset system) {
		QuestionScoreMap scoreMap = new QuestionScoreMap();
		for(Question benchmarkQ : benchmark.getQuestion()) {
			Question systemQ = getQuestion(benchmarkQ.getId(), system);
			double qScore = 0;
			if(systemQ != null) {
				qScore = evaluate(benchmarkQ, systemQ);
			}
			scoreMap.put(benchmark.getId(), qScore);
		}
		return scoreMap;
	}
	
	public double evaluate(Question benchmark, Question system) {
		List<Answer> benchmarkAnswerList = benchmark.getAnswers().getAnswer();
		List<Answer> systemAnswerList = system.getAnswers().getAnswer();
		if(benchmarkAnswerList.size() == 0 
				&& systemAnswerList.size() == 0) {
			return 1;
		}
		return evaluateAnswerList(benchmarkAnswerList, systemAnswerList);
	}	
	
	public double evaluateAnswerList(List<Answer> benchmarkAnswerList, List<Answer> systemAnswerList) {
		List<String> benchmarkList = answerList2StringList(benchmarkAnswerList);
		List<String> systemList = answerList2StringList(systemAnswerList);
		return evaluate(benchmarkList, systemList);
	}
	
	protected List<String> answerList2StringList(List<Answer> answers) {
		List<String> stringList = new ArrayList<String>();
		for(Answer a : answers) {
			stringList.add(a.getValue());
		}		
		return stringList;
	}
	
	public abstract double evaluate(List<String> benchAnswerList, List<String> systemAnswerList);

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
