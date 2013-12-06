/*
The MIT License (MIT)

Copyright (c) 2013 Sergey Nechaev <serg.nechaev@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.search4j.model;

import java.util.regex.Pattern;

/**
 * 
 * @author Sergey.Nechaev <serg.nechaev@gmail.com>
 *
 */
public class Search4JModel {

	private String query;
	private String text;
	private boolean matchWholeWordOnly;
	private boolean caseSensitive;
	private boolean regularExpression;
	private Pattern pattern;
	private Pattern wholeWordPattern;

	public Search4JModel(String query, boolean matchWholeWordOnly, boolean caseSensitive, boolean regularExpression) {
		this.setMatchWholeWordOnly(matchWholeWordOnly);
		this.setCaseSensitive(caseSensitive);
		this.setRegularExpression(regularExpression);
		this.setQuery(query);
	}
	
	@Override
	protected Search4JModel clone() throws CloneNotSupportedException {
		return new Search4JModel(query, matchWholeWordOnly, caseSensitive, regularExpression);
	}	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = this.isCaseSensitive() ? text : text.toLowerCase();
	}

	public String getQuery() {
		return query;
	}

	public Pattern getQueryAsCompiledPattern() {
		return pattern;
	}

	public Pattern getQueryAsCompiledWholeWordPattern() {
		return wholeWordPattern;
	}

	private void setQuery(String query) {

		if (this.isRegularExpression()) {
			this.setCaseSensitive(false);
		}

		this.query = this.isCaseSensitive() ? query : query.toLowerCase();
		try {
			this.pattern = Pattern.compile(this.isCaseSensitive() ? query : query.toLowerCase());
		} catch (Exception e) {
		}
		try {
			this.wholeWordPattern = Pattern.compile("\\b(" + (this.isCaseSensitive() ? query : query.toLowerCase()) + ")\\b");
		} catch (Exception e) {
		}
	}

	public boolean isMatchWholeWordOnly() {
		return matchWholeWordOnly;
	}

	private void setMatchWholeWordOnly(boolean matchWholeWordOnly) {
		this.matchWholeWordOnly = matchWholeWordOnly;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	private void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public boolean isRegularExpression() {
		return regularExpression;
	}

	private void setRegularExpression(boolean regularExpression) {
		this.regularExpression = regularExpression;
	}

	@Override
	public String toString() {
		return "SearchModel [query=" + query + ", text=" + text + ", matchWholeWordOnly=" + matchWholeWordOnly + ", caseSensitive=" + caseSensitive
				+ ", regularExpression=" + regularExpression + ", pattern=" + pattern + ", wholeWordPattern=" + wholeWordPattern + "]";
	}

}
