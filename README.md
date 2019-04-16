# orbit
Orbit (Open Information Retrieval Framework) is a general framework for testing, benchmarking and developing IR systems.


## Benchmarking

Orbit bridges different benchmark datasets such as TREC and QALD.
It contain the main features necessary to benchmark IR systems.
It implements parsers for the two main benchmark dataset available TREC and QALD.
It contains a list of five meaures that can be used to evaluate the system answers: MAP@?, P@?, Precision, Recall, and F-measure.
It also have 9 emmbeded QALD test datasets.
Below you find a list of benchmark functionalities implemented.

```

```


- [Listing available datasets?](https://github.com/AKSW/KBox#why-use-kbox)
- [Listing available measures?](https://github.com/AKSW/KBox#what-is-possible-to-do-with-it)
- [Evaluating](https://github.com/AKSW/KBox#how-can-i-use-kbox)
- [Outputing dataset(s) content(s)](https://github.com/AKSW/KBox#how-can-i-execute-kbox-in-command-line)

### Listing available datasets


```
java -jar orbit.benchmark.jar -datasets
```

### Listing available measures

```
java -jar orbit.benchmark.jar -measures
```

### Evaluating

```
java -jar -evaluate "qald-8-test-multilingual" "qald-8-test-multilingual.qald.json" "MAP@10"
|	Q-ID	|	MAP@10	|
|	22	|	1.0	|
...
|	21	|	1.0	|
|	43	|	1.0	|
|	AVG	|	1.0	|

```

You can also have the evaluation output in latex, using the pragma -latex.

```
java -jar -evaluate "qald-8-test-multilingual" "qald-8-test-multilingual.qald.json" "MAP@10" -latex
\begin{center}
\begin{tabular}{| c | c |}
\hline\hline
	Q	&	MAP@10\\
\hline\hline
22	&	1.0	 \\
44	&	1.0	 \\
45	&	1.0	 \\
...
```

### Outputing dataset(s) content(s)

```
java -jar orbit.benchmark.jar -print
```
