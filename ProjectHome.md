## Simple text search library for Java ##

The search is performed on in-memory String objects or File and Folders with the following options:

  * Match **whole word**
  * Match **case**
  * By **regular expression**


## Downloads ##

http://google-code.s3.amazonaws.com/com.search4j.library-1.0.0.jar


## Search in String objects ##

```
	String text = ...

	String query = ...

	Search4JModel model = new Search4JModel(query, false, false, false);

	List<Search4JResult> find = new Search4J().find(model, text);
```



## Search in a file ##

```
	String query = ...

	Search4JModel model = new Search4JModel(query, false, false, false);

	new Search4J().find(model, file, "UTF-8", null, new Search4JProgressCallback() {...});
```



## Search in files in directory ##

```
	String query = ...

	Search4JModel model = new Search4JModel(query, false, false, false);

	new Search4J().find(model, file, "UTF-8", false, null, new Search4JProgressCallback() {...});
```


## Search in files in directory and sub-directory ##

```
	String query = ...

	Search4JModel model = new Search4JModel(query, false, false, false);

	new Search4J().find(model, file, "UTF-8", true, null, new Search4JProgressCallback() {...});
```


## Search progress callback ##

Method **found** is called by Search4J when a new match is found. **Finished** is called when the search finishes and no new results are expected. **Current value** is called for file searches and the current _file line number_ is passed.

```
	package com.search4j.model;

	public interface Search4JProgressCallback {

		void found(Search4JResult result);

		void finished();

		void currentValue(int lineNum);
	
	}
```


## Cancellation ##

```
	com.search4j.model.Search4JCancellationEvent
```

When doing search in large files, you might want to give the user a chance to cancel the search - via UI button, for example. This object will advise Search4J that the current search must be immediately terminated
