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
package com.search4j.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.search4j.model.Search4JModel;
import com.search4j.model.Search4JResult;

/**
 * 
 * @author Sergey.Nechaev <serg.nechaev@gmail.com>
 * 
 */
public class Search4JHelper {

	private static final Log log = LogFactory.getLog(Search4JHelper.class);

	public static List<Search4JResult> find(Search4JModel model, String text) {

		model.setText(text);

		List<Search4JResult> results = null;

		try {
			if (model.isRegularExpression()) {
				results = regexp(model);
			} else {
				results = text(model);
			}
		} catch (Exception e) {
			log.error("Error: ", e);
		}

		return results;

	}

	public static int count(Search4JModel model) {

		try {
			if (model.isRegularExpression()) {
				return regexpCount(model);
			} else {
				return textCount(model);
			}
		} catch (Exception e) {
			log.error("Error: ", e);
		}

		return 0;

	}

	private static int textCount(Search4JModel model) {
		if (model.isMatchWholeWordOnly()) {
			final Matcher matcher = model.getQueryAsCompiledWholeWordPattern().matcher(model.getText());
			return parseMatcherForCount(matcher);
		} else {
			return StringUtils.countMatches(model.getText(), model.getQuery());
		}
	}

	private static int parseMatcherForCount(Matcher matcher) {

		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}

	private static int regexpCount(Search4JModel model) {

		int count = 0;

		if (model.getQueryAsCompiledPattern() != null) {
			Matcher matcher = model.getQueryAsCompiledPattern().matcher(model.getText());
			count = parseMatcherForCount(matcher);
		}

		return count;
	}

	private static List<Search4JResult> text(Search4JModel model) {

		final String q = model.getQuery();
		final String t = model.getText();

		final int queryLength = q.length();

		final List<Search4JResult> results = new ArrayList<Search4JResult>();

		if (false == model.isMatchWholeWordOnly()) {

			int index = StringUtils.indexOf(t, q, 0);

			while (index > -1) {

				final Search4JResult result = new Search4JResult();

				result.setStart(index);
				result.setEnd(index + queryLength);
				results.add(result);

				index = StringUtils.indexOf(t, q, index + queryLength);
			}

		} else {

			final Matcher matcher = model.getQueryAsCompiledWholeWordPattern().matcher(t);

			parseMatcher(results, matcher);

		}

		return results;
	}

	private static void parseMatcher(final List<Search4JResult> results, final Matcher matcher) {

		while (matcher.find()) {
			final Search4JResult result = new Search4JResult();

			result.setStart(matcher.start());
			result.setEnd(matcher.end());

			results.add(result);
		}
	}

	private static List<Search4JResult> regexp(Search4JModel model) {

		final List<Search4JResult> results = new ArrayList<Search4JResult>();

		if (model.getQueryAsCompiledPattern() != null) {
			Matcher matcher = model.getQueryAsCompiledPattern().matcher(model.getText());
			parseMatcher(results, matcher);
		}

		return results;
	}

}
