package org.aksw.orbit.benchmark.prompt;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.aksw.orbit.benchmark.measure.FMeasure;
import org.aksw.orbit.benchmark.measure.MAP;
import org.aksw.orbit.benchmark.measure.Measure;
import org.aksw.orbit.benchmark.measure.Precision;
import org.aksw.orbit.benchmark.measure.PrecisionAt;
import org.aksw.orbit.benchmark.measure.Recall;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.dataset.parser.DatasetParser;
import org.aksw.orbit.dataset.parser.DatasetParserFactory;

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
		
		Map<String, Dataset> datasets = new HashMap<>();
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
		}
		
		CommandOption measuresOption = new PrintListCommand("-measures", measures.keySet());
		CommandOption datasetsOption = new PrintListCommand("-datasets", datasets.keySet());
		CommandOption printOption = new PrintQuestionsCommand("-questions", datasets);
		CommandOption evaluateOption = new EvaluateCommand("-evaluate", measures, datasets);
		CommandFactory factory = new CommandFactory(measuresOption, 
				datasetsOption, 
				printOption, 
				evaluateOption);
		CommandOption option = factory.getCommandOption(args);
		if(option == null) {
			help();
		} else {
			option.process(args);
		}
	}
	
	private static void addDataset(Map<String, Dataset> datasets, URL datasetURL) throws Exception {
		DatasetParserFactory paserFactory = new DatasetParserFactory();
		DatasetParser parser = paserFactory.getDatasetParser(datasetURL);
		Dataset dataset = parser.parse(datasetURL);
		datasets.put(dataset.getId(), dataset);
	}

	public static void help() {
		System.out.println("java -jar orbit.benchmark.jar [option]:");
		System.out.println("-evaluate\t<benchmarkAnswerFile|datasetID>\t<systemAnswerFile>\t<\"measure1\",\"measure2\">\t[-latex|-json]");
		System.out.println("-datasets\tList available datasets for evaluation.");
		System.out.println("-measures\tList available measures.");
		System.out.println("-questions\t[\"datasetFile1\",\"datasetFile2\",\"datasetID1\"...]\t[-format <format>]");
	}
}
