package org.aksw.orbit.benchmark.prompt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.DatasetVisitor;
import org.aksw.orbit.benchmark.qald.schema.Keywords;
import org.aksw.orbit.benchmark.qald.schema.Question;
import org.apache.commons.text.StringEscapeUtils;

public class PrintDatasetCommand extends AbstractSimpleMapCommandOption implements DatasetVisitor {

	private Map<String,Dataset> datasets;
	private String format = "{\"datasetId\":\"?datasetId\",\"question\":\"?question\", \"keywords\":\"?keywords\", "
			+ "\"sparql\":\"?sparql\" , \"lang\":\"?lang\"}";
	
	private String datasetID = null;	
	private String lang = null;
	
	public PrintDatasetCommand(String option, Map<String,Dataset> datasets) {
		super(option);
		this.datasets = datasets;
	}

	@Override
	public void process(Map<String, String[]> commands) throws Exception {
		String[] formatParams = commands.get("-format");
		if(formatParams != null) {
			String userFormat = formatParams[0];
			if(userFormat != null) {
				format = userFormat; 
			}
		}
		
		for(String datasetID : datasets.keySet()) {
			Dataset dataset = datasets.get(datasetID);
			dataset.accept(this);
		}
	}

	@Override
	public void visit(Dataset dataset) throws Exception {
		this.datasetID = dataset.getId();
	}

	@Override
	public void visit(Question q) {
		List<org.aksw.orbit.benchmark.qald.schema.String> stringElements = q.getString();
		Map<String, String> keywordMap = getKeywordMap(q.getKeywords());
		for(org.aksw.orbit.benchmark.qald.schema.String query : stringElements) {
			String row = new String(format);
			if((lang == null || query.getLang() == null ) || query.getLang().equals(lang)) {
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
				row = row.replace("?question", StringEscapeUtils.escapeJson(question));
				row = row.replace("?sparql", StringEscapeUtils.escapeJson(sparql));
				row = row.replace("?lang", StringEscapeUtils.escapeJson(queryLang));
				row = row.replace("?keywords", StringEscapeUtils.escapeJson(keywords));
				System.out.println(row);
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
