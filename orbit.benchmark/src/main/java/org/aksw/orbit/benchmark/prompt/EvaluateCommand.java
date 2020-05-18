package org.aksw.orbit.benchmark.prompt;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.aksw.orbit.benchmark.Evaluator;
import org.aksw.orbit.benchmark.LatexEvaluationEncoder;
import org.aksw.orbit.benchmark.TabularEvaluationEncoder;
import org.aksw.orbit.benchmark.measure.Measure;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.dataset.parser.DatasetParser;
import org.aksw.orbit.dataset.parser.DatasetParserFactory;

public class EvaluateCommand extends AbstractSimpleMapCommand {
	
	public final static String MEASURE_COMMAND_SEPARATOR = ",";
	public final static String LATEX_COMMAND = "-latex";
	private Map<String, Measure> measures;
	private Map<String, Dataset> datasets;

	public EvaluateCommand(String option, Map<String, Measure> measures, Map<String, Dataset> datasets) {
		super(option);
		this.measures = measures;
		this.datasets = datasets;
	}
	
	@Override
	public boolean canProcess(Map<String, String[]> commands) {
		return super.canProcess(commands) && commands.get(getOption()).length >= 3;
	}

	@Override
	public void process(Map<String, String[]> commands) throws Exception {
		String[] evalParams = commands.get(getOption());
		String benchmarkParam = evalParams[0];
		Dataset benchDataset = null;
		DatasetParserFactory parserFactory = new DatasetParserFactory();
		if(datasets.containsKey(benchmarkParam)) {
			benchDataset = datasets.get(benchmarkParam);
		} else {
			File benchFilePath = new File(evalParams[0]);
			DatasetParser parser =  parserFactory.getDatasetParser(benchFilePath);
			if(parser == null) {
				System.out.println("Dataset format cannot be recognized, check if the file contain the right ending.");
				System.out.println("Supported formats \".a.trec\", \".qald.xml\", and \".qald.json\"");
				return;
			}
			benchDataset = parser.parse(benchFilePath);
		}
		File systemFilePath = new File(evalParams[1]);
		String[] measureIDs = evalParams[2].split(MEASURE_COMMAND_SEPARATOR);
		Map<String, Measure> selectedMeasures = new HashMap<>();
		for(String measureID : measureIDs) {
			Measure measure = measures.get(measureID);
			selectedMeasures.put(measureID, measure);
		}
		DatasetParser parser =  parserFactory.getDatasetParser(systemFilePath);
		Dataset systemDataset = parser.parse(systemFilePath);
		Evaluator evaluator = new Evaluator(systemDataset, selectedMeasures);
		benchDataset.accept(evaluator);
		if(commands.containsKey(LATEX_COMMAND)) {
			System.out.println(evaluator.getEvaluation(new LatexEvaluationEncoder()));
		} else {
			System.out.println(evaluator.getEvaluation(new TabularEvaluationEncoder()));
		}
	}
}
