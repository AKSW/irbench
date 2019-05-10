package org.aksw.orbit.benchmark.prompt;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.aksw.orbit.benchmark.DBpediaEntityQALD2Dataset;
import org.aksw.orbit.benchmark.measure.FMeasure;
import org.aksw.orbit.benchmark.measure.MAP;
import org.aksw.orbit.benchmark.measure.Measure;
import org.aksw.orbit.benchmark.measure.Precision;
import org.aksw.orbit.benchmark.measure.PrecisionAt;
import org.aksw.orbit.benchmark.measure.Recall;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.utils.DatasetUtils;

public class Main {	
	
	public static void main(String[] args) throws Exception {
		Map<String, Measure> measures = new HashMap<>();
		{
			measures.put("f-score", new FMeasure());
			measures.put("precision", new Precision());
			measures.put("recall", new Recall());
			measures.put("MAP@10", new MAP(10));
			measures.put("P@10", new PrecisionAt(10));
			measures.put("MAP@100", new MAP(100));
			measures.put("P@100", new PrecisionAt(100));
		}
		
		String qald2DatasetId = "QALD2-DBpedia39-Entity-v1";
		Map<String, Dataset> datasets = new HashMap<String, Dataset>();
		{
			addDataset(datasets, Main.class.getResource("/org/aksw/orbit/benchmark/qald-1_multilingual_test_answers.qald.xml"));
			addDataset(datasets, Main.class.getResource("/org/aksw/orbit/benchmark/qald-2_multilingual_test_answers.qald.xml"));
			addDataset(datasets, Main.class.getResource("/org/aksw/orbit/benchmark/qald-3_multilingual_test_answers.qald.xml"));
			addDataset(datasets, Main.class.getResource("/org/aksw/orbit/benchmark/qald-4_multilingual_test_answers.qald.xml"));
			addDataset(datasets, Main.class.getResource("/org/aksw/orbit/benchmark/qald-5_multilingual_test_answers.qald.xml"));
			addDataset(datasets, Main.class.getResource("/org/aksw/orbit/benchmark/qald-6-test-multilingual.qald.json"));
			addDataset(datasets, Main.class.getResource("/org/aksw/orbit/benchmark/qald-7-test-multilingual.qald.json"));
			addDataset(datasets, Main.class.getResource("/org/aksw/orbit/benchmark/qald-8-test-multilingual.qald.json"));
			addDataset(datasets, Main.class.getResource("/org/aksw/orbit/benchmark/qald-9-test-multilingual.qald.json"));
			addDataset(datasets, "DBpedia39-Entity-v1", Main.class.getResource("/org/aksw/orbit/benchmark/qrels-v1_39.a.trec"));
			Dataset dataset = DatasetUtils.getDataset(Main.class.getResource("/org/aksw/orbit/benchmark/qrels-v1_39.a.trec"));
			Dataset dbpediaEntityQALD2Dataset = new DBpediaEntityQALD2Dataset(dataset);		
			dbpediaEntityQALD2Dataset.setId(qald2DatasetId);
			datasets.put(qald2DatasetId, dbpediaEntityQALD2Dataset);			
			addDataset(datasets, "DBpedia2015-Entity-v1", Main.class.getResource("/org/aksw/orbit/benchmark/qrels-v1_2015_10.a.trec"));
		}
		
		Map<String, Dataset> dataq = new HashMap<String, Dataset>();
		{
			dataq.putAll(datasets);
			addDataset(dataq, "DBpedia39-Entity-v1", Main.class.getResource("/org/aksw/orbit/benchmark/questions.q.trec"));
			addDataset(dataq, "DBpedia2015-Entity-v1", Main.class.getResource("/org/aksw/orbit/benchmark/questions.q.trec"));
			Dataset dataset = DatasetUtils.getDataset(Main.class.getResource("/org/aksw/orbit/benchmark/questions.q.trec"));
			Dataset dbpediaEntityQALD2Dataset = new DBpediaEntityQALD2Dataset(dataset);
			dbpediaEntityQALD2Dataset.setId(qald2DatasetId);
			dataq.put(qald2DatasetId, dbpediaEntityQALD2Dataset);
		}
		
		Command measuresOption = new PrintListCommand("-measures", measures.keySet());
		Command datasetsOption = new PrintListCommand("-datasets", datasets.keySet());
		Command printOption = new PrintQuestionsCommand("-questions", dataq);
		Command evaluateOption = new EvaluateCommand("-evaluate", measures, datasets);
		CommandFactory factory = new CommandFactory(measuresOption, 
				datasetsOption, 
				printOption, 
				evaluateOption);
		Command option = factory.getCommand(args);
		if(option == null) {
			help();
		} else {
			option.process(args);
		}
	}
	
	private static void addDataset(Map<String, Dataset> datasets, URL datasetURL) throws Exception {
		Dataset dataset = DatasetUtils.getDataset(datasetURL);
		datasets.put(dataset.getId(), dataset);
	}
	
	private static void addDataset(Map<String, Dataset> datasets, String id, URL datasetURL) throws Exception {
		Dataset dataset = DatasetUtils.getDataset(datasetURL);
		dataset.setId(id);
		datasets.put(id, dataset);
	}

	public static void help() {
		System.out.println("java -jar orbit.benchmark.jar [option]:");
		System.out.println("-evaluate\t<benchmarkAnswerFile|datasetID>\t<systemAnswerFile>\t<\"measure1\",\"measure2\">\t[-latex|-json]");
		System.out.println("-datasets\tList available datasets for evaluation.");
		System.out.println("-measures\tList available measures.");
		System.out.println("-questions\t[\"datasetFile1\",\"datasetFile2\",\"datasetID1\"...]\t[-format <format>]");
	}
}
