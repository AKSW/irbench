package org.aksw.orbit.dataset.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.Keywords;
import org.aksw.orbit.benchmark.qald.schema.Question;

public class TRECQueriesParser extends AbstractDatasetParser {

	@Override
	public Dataset parse(InputStream is) throws Exception {
		Map<String, String> questions = getQuestions(is);
		
		Dataset dataset = new Dataset();
		
		List<Question> datasetQuestions = dataset.getQuestion();
		
		for(Entry<String, String> entry : questions.entrySet()) {
			Question q = new Question();
			q.setId(entry.getKey());
			q.setAnswertype("resource");
			Keywords keywords = new Keywords();
			keywords.setLang("en");
			keywords.setValue(entry.getValue());
			q.getKeywords().add(keywords);
			datasetQuestions.add(q);
		}
		
		return dataset;
	}
	
	private static Map<String, String> getQuestions(InputStream is) throws Exception {
		Map<String, String> questionMap = new HashMap<String, String>();
		try (InputStreamReader reader = new InputStreamReader(is)){
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] entries = line.split("\t");
				if(entries.length != 2) {
					throw new Exception("Invalid entry size on line :" + line );
				}
				questionMap.put(entries[0], entries[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return questionMap;
	}

}
