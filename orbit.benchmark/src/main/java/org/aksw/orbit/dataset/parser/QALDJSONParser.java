package org.aksw.orbit.dataset.parser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.aksw.orbit.benchmark.qald.schema.Answer;
import org.aksw.orbit.benchmark.qald.schema.Answers;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.Keywords;
import org.aksw.orbit.benchmark.qald.schema.Question;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class QALDJSONParser extends AbstractDatasetParser {
	
	private final static Logger logger = Logger.getLogger(QALDJSONParser.class);

	@Override
	public Dataset parse(InputStream is) throws JSONException {
		InputStreamReader isReader = new InputStreamReader(is);
		JSONTokener tokener = new JSONTokener(isReader);
		JSONObject object = new JSONObject(tokener);
		
		Dataset schemaDataset = new Dataset();
		JSONObject dataset = object.getJSONObject(QALDJSON.DATASET_PARAM);
		String datasetID = dataset.getString(QALDJSON.ID_PARAM);
		schemaDataset.setId(datasetID);
		JSONArray questions = object.getJSONArray(QALDJSON.QUESTIONS_PARAM);
		for(int i=0; i < questions.length(); i++) {
			Question q = new Question();
			JSONObject question = questions.getJSONObject(i);
			String id = question.getString(QALDJSON.ID_PARAM);
			q.setId(id);
			String answerType = question.getString(QALDJSON.ANSWER_TYPE_PARAM);
			q.setAnswertype(answerType);
			String aggregtion = question.getString(QALDJSON.AGGREGATION_PARAM);
			q.setAggregation(aggregtion);
			String onlydbo = question.getString(QALDJSON.ONLYDBO_PARAM);
			q.setOnlydbo(onlydbo);
			String hybrid = question.getString(QALDJSON.HYBRID_PARAM);
			q.setHybrid(hybrid);
			JSONObject query = question.getJSONObject(QALDJSON.QUERY_PARAM);
			if(query.has(QALDJSON.SPARQL_PARAM)) {
				String sparql = query.getString(QALDJSON.SPARQL_PARAM);
				q.setQuery(sparql);
			}
			
			JSONArray langQuestions = question.getJSONArray(QALDJSON.QUESTION_PARAM);
			java.util.List<org.aksw.orbit.benchmark.qald.schema.String> strings = new ArrayList<org.aksw.orbit.benchmark.qald.schema.String>();
			java.util.List<Keywords> keywordsList = new ArrayList<Keywords>();
			for(int j=0; j < langQuestions.length(); j++) {
				org.aksw.orbit.benchmark.qald.schema.String schemaLangQuestion = new org.aksw.orbit.benchmark.qald.schema.String();
				JSONObject langQuestion = langQuestions.getJSONObject(j);
				String language = langQuestion.getString(QALDJSON.LANGUAGE_PARAM);
				schemaLangQuestion.setLang(language);
				if(langQuestion.has(QALDJSON.STRING_PARAM)) {
					String string = langQuestion.getString(QALDJSON.STRING_PARAM);
					schemaLangQuestion.setValue(string);
				} else {
					logger.warn(datasetID + ": There is no 'string' tag for question:" + id + " lang:" + language);
				}
				strings.add(schemaLangQuestion);
				Keywords schemaLangKeywords = new Keywords();
				if(langQuestion.has(QALDJSON.KEYWORDS_PARAM)) {
					String keywords = langQuestion.getString(QALDJSON.KEYWORDS_PARAM);
					schemaLangKeywords.setValue(keywords);
				} else {
					logger.warn(datasetID + ": There is no 'keywords' tag for question:" + id + " lang:" + language);
				}
				
				schemaLangKeywords.setLang(language);
				keywordsList.add(schemaLangKeywords);
			}
			q.getKeywords().addAll(keywordsList);
			q.getString().addAll(strings);

			JSONArray answers = question.getJSONArray(QALDJSON.ANSWERS_PARAM);
			Answers schemaAnswers = new Answers();
			for(int j=0; j < answers.length(); j++) {
				JSONObject answer = answers.getJSONObject(j);
				if(answer.has(QALDJSON.RESULTS_PARAM)) {
					JSONObject results = answer.getJSONObject(QALDJSON.RESULTS_PARAM);
					Answer schemaAnswer = new Answer();
					if(results.has(QALDJSON.BINDINGS_PARAM)) {
						JSONArray bindings = results.getJSONArray(QALDJSON.BINDINGS_PARAM);				
						for(int k=0; k < bindings.length(); k++) {
							JSONObject result = bindings.getJSONObject(k);
							String[] names = JSONObject.getNames(result);
							for(String name : names) {
								JSONObject attr = result.getJSONObject(name);
								String type = attr.getString(QALDJSON.TYPE_PARAM);
								String value = attr.getString(QALDJSON.VALUE_PARAM);
								if(QALDJSON.URI_PARAM.equals(type)) {
									schemaAnswer.setUri(value);
								} else if(QALDJSON.LITERAL_PARAM.equals(type)) {
									schemaAnswer.setLiteral(value);
								}
							}
						}
					} else if (answer.has(QALDJSON.BOOLEAN_PARAM)) {
						String bool = answer.getString(QALDJSON.BOOLEAN_PARAM);
						schemaAnswer.setBoolean(bool);
					}
					schemaAnswers.getAnswer().add(schemaAnswer);
				}
			}
			q.setAnswers(schemaAnswers);
			schemaDataset.getQuestion().add(q);
		}
		return schemaDataset;
	}

}
