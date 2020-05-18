package org.aksw.orbit.benchmark.prompt;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aksw.orbit.benchmark.qald.schema.Answer;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.DatasetVisitor;
import org.aksw.orbit.benchmark.qald.schema.Keywords;
import org.aksw.orbit.benchmark.qald.schema.Question;
import org.aksw.orbit.utils.DatasetUtils;
import org.apache.commons.text.StringEscapeUtils;

public class PrintQuestionsCommand extends AbstractSimpleMapCommand implements DatasetVisitor {
	
	private Map<String,Dataset> datasets;
	private String format = "{\"datasetId\":\"?datasetId\",\"questionId\":\"?questionId\", \"question\":\"?question\", \"answers\":[?answers], \"keywords\":\"?keywords\", "
			+ "\"sparql\":\"?sparql\" , \"lang\":\"?lang\"}";
	
	private String trecAnswerFormat = "?questionId Q0 <?value> ?index ?rank ?approach";
	
	private final static String FORMAT_PARAM = "-format";
	private final static String LANG_PARAM = "-lang";
	private final static String DATASET_SPLIT_PRAGMA = ",";
	
	private String datasetID = null;
	private String lang = null;
	
	public PrintQuestionsCommand(String option, Map<String,Dataset> datasets) {
		super(option);
		this.datasets = datasets;
	}

	@Override
	public void process(Map<String, String[]> commands) throws Exception {
		String[] formatParams = commands.get(FORMAT_PARAM);
		if(formatParams != null) {
			String userFormat = formatParams[0];
			if(userFormat != null) {
				format = userFormat; 
			}
		}
		String[] langParams = commands.get(LANG_PARAM);
		if(langParams != null) {
			String lang = langParams[0];
			if(lang != null) {
				this.lang = lang; 
			}
		}
		Collection<String> selectedDatasets = datasets.keySet();
		String[] selectedDatasetsOption = commands.get(getOption());
		if(selectedDatasetsOption.length > 0) {
			selectedDatasetsOption = selectedDatasetsOption[0].split(DATASET_SPLIT_PRAGMA);
			selectedDatasets = Arrays.asList(selectedDatasetsOption);
		}
		for(String datasetID : selectedDatasets) {
			Dataset dataset = datasets.get(datasetID);
			if(dataset == null) {
				File datasetFile = new File(datasetID);
				if(datasetFile.exists()) {
					dataset = DatasetUtils.getDataset(datasetFile.toURI().toURL());
				}
			}
			if(dataset != null) {
				dataset.accept(this);
			}
		}
	}

	@Override
	public void visit(Dataset dataset) throws Exception {
		this.datasetID = dataset.getId();
	}

	@Override
	public void visit(Question q) {
		if(format.equals("trec.a")) {
			int i = 0;
			for(Answer a : q.getAnswers().getAnswer()) {
				String row = new String(trecAnswerFormat);
				row = row.replace("?questionId", q.getId());
				row = row.replace("?value", StringEscapeUtils.escapeJson(a.getValue()));
				row = row.replace("?index", Integer.toString(i));
				row = row.replace("?rank", Integer.toString(i));
				row = row.replace("?approach", "unknown");
				System.out.println(row);
				i++;
			}
		} else  {
			List<org.aksw.orbit.benchmark.qald.schema.String> stringElements = q.getString();
			Map<String, String> keywordMap = getKeywordMap(q.getKeywords());
			for(org.aksw.orbit.benchmark.qald.schema.String query : stringElements) {
				if((lang == null || query.getLang() == null ) || query.getLang().equals(lang)) {
						String row = new String(format);
						String question = query.getValue();
						String sparql = q.getQuery();
						String queryLang = query.getLang();
						String copyDatasetID = new String(datasetID);
						String keywords = keywordMap.get(queryLang);
						if(sparql == null) {
							sparql = "";
						}
						if(question == null) {
							question = "";
						}
						if(queryLang == null) {
							queryLang = "";
						} 
						if(keywords == null) {
							keywords = "";
						}						
						row = row.replace("?datasetId", StringEscapeUtils.escapeJson(copyDatasetID));
						row = row.replace("?questionId", StringEscapeUtils.escapeJson(q.getId()));
						row = row.replace("?question", StringEscapeUtils.escapeJson(question));						
						row = row.replace("?sparql", StringEscapeUtils.escapeJson(sparql));
						row = row.replace("?lang", StringEscapeUtils.escapeJson(queryLang));
						row = row.replace("?keywords", StringEscapeUtils.escapeJson(keywords));
						if(q.getAnswers() != null) {
							String answers = new String();
							for(Answer a : q.getAnswers().getAnswer()) {
								answers += "\"" + StringEscapeUtils.escapeJson(a.getValue()) + "\",";
							}
							if(answers != null && answers.length() > 0) {
								answers = answers.substring(0, answers.length()-1);
							}
							row = row.replace("?answers", answers);
							
						} else {
							row = row.replace("?answers", "");
						}
						System.out.println(row);
				}
			}
		}
	}

	private Map<String, String> getKeywordMap(List<Keywords> keywordsList) {
		Map<String, String> keywordsMap = new HashMap<>();
		for(Keywords keywords : keywordsList) {
			keywordsMap.put(keywords.getLang(), keywords.getValue());
		}
		return keywordsMap;
	}
}
