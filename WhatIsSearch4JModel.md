# What is Search4JModel #

**Search4JModel** is used to advise **Search4J** on the details of how the search should be executed.

1. Create a new model object using the constructor:

```java

public Search4JModel(String query, boolean matchWholeWordOnly, boolean caseSensitive, boolean regularExpression)```

If the **regularExpression** argument is _true_, then the library will ignore **matchWholeWordOnly** and **caseSensitive** fields.

2. Set the **text** property that will be used to match the **query**:

```java

model.setText("Text I want to search in");```

Please note, that **Search4JModel** is an immutable object where only the **text** property can be changed.

The creation of a new **Search4JModel** object is relatively expensive (as the regular expression/match whole word pattern fields are pre-compiled at the construction time).

So, if you are doing thousands of searches, re-use the same model if possible. However, if the design of your application doesn't allow it, please measure the performance before making any radical changes.

To make a copy of an existing **Search4JModel** object, please use its **clone** method.