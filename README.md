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


### Outputing dataset(s) content(s)

```
java -jar orbit.benchmark.jar -print
```
