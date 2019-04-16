package org.aksw.orbit.dataset.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.aksw.orbit.benchmark.qald.schema.Answer;
import org.aksw.orbit.benchmark.qald.schema.Answers;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.Question;

public class TRECAnswersParser extends AbstractDatasetParser {

	@Override
	public Dataset parse(InputStream is) throws Exception {
		Map<String, List<String>> answers = getAnswers(is);
		
		Dataset dataset = new Dataset();
		
		List<Question> datasetQuestions = dataset.getQuestion();
		
		for(Entry<String, List<String>> entry : answers.entrySet()) {
			Question q = new Question();
			q.setId(entry.getKey());
			List<String> answerList = answers.get(entry.getKey());
			Answers datasetAnswers = new Answers();
			for(String resource : answerList) {
				Answer answer = new Answer();
				answer.setUri(resource);
				datasetAnswers.getAnswer().add(answer);
			}
			q.setAnswers(datasetAnswers);
			datasetQuestions.add(q);
		}		
		return dataset;
	}	
	
	private static Map<String, List<String>> getAnswers(InputStream is) {
		Map<String, List<String>> questionMap = new HashMap<String, List<String>>();
		try (InputStreamReader reader = new InputStreamReader(is)){			
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] entries = line.split("\t");
				String resource = entries[2].replace("<", "").
						replace(">", "").
						replace("dbpedia:", "http://dbpedia.org/resource/");
				List<String> answerList = questionMap.get(entries[0]);
				if(answerList == null) {
					answerList = new ArrayList<String>();
					questionMap.put(entries[0], answerList);
				}
				answerList.add(resource);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return questionMap;
	}

}
