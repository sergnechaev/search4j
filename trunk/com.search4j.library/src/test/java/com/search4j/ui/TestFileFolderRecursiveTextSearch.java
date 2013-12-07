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
package com.search4j.ui;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

import com.search4j.Search4J;
import com.search4j.model.Search4JCancellationEvent;
import com.search4j.model.Search4JModel;
import com.search4j.model.Search4JProgressCallback;
import com.search4j.model.Search4JResult;

public class TestFileFolderRecursiveTextSearch extends TestCase {

	public void testBasic() throws IOException, URISyntaxException {

		String[] queries = { "storks", "because the jacket", "Eĥoŝanĝo ĉiuĵaŭde", "rvíztűr", "Thursda", "traditional", "Storks", "because the JACKET", "Eĥoŝanĝo ĉiUĵaŭde", "Rvíztűr", "thursda",
				"TRaditional" };

		for (String query : queries) {

			Search4JModel model = new Search4JModel(query, false, false, false);

			SPC spc = new SPC();

			new Search4J().find(model, new File("D:/oss/com.search4j.library/src/test/resources/files/11/"), "UTF-8", true, new Search4JCancellationEvent(), spc);

			assertTrue(spc.getResults().size() == 2);

			for (Search4JResult r : spc.getResults()) {
				boolean e = StringUtils.equalsIgnoreCase(query, StringUtils.substring(r.getText(), r.getStart(), r.getEnd()));
				assertTrue(e);
			}

		}

	}

	public void testMatchWholeWord() throws IOException, URISyntaxException {

		String[] queries = { "stor", "because the jacke", "Eĥoŝanĝo ĉiuĵaŭd", "rvíztű", "Thursda", "traditiona" };

		for (String query : queries) {

			Search4JModel model = new Search4JModel(query, true, false, false);

			SPC spc = new SPC();

			new Search4J().find(model, new File("D:/oss/com.search4j.library/src/test/resources/files/11/"), "UTF-8", true, new Search4JCancellationEvent(), spc);

			assertTrue(spc.getResults().size() == 0);

		}

	}

	public void testMatchCase() throws IOException, URISyntaxException {

		String[] queries = { "Storks", "because the JACKET", "Eĥoŝanĝo ĉiUĵaŭde", "Rvíztűr", "thursda", "TRaditional" };

		for (String query : queries) {

			Search4JModel model = new Search4JModel(query, false, true, false);

			SPC spc = new SPC();

			new Search4J().find(model, new File("D:/oss/com.search4j.library/src/test/resources/files/11/"), "UTF-8", true, new Search4JCancellationEvent(), spc);

			assertTrue(spc.getResults().isEmpty());
		}
	}

	private static class SPC implements Search4JProgressCallback {

		int size;

		int lines;

		List<Search4JResult> results = new ArrayList<Search4JResult>();

		public List<Search4JResult> getResults() {
			return results;
		}

		public int getSize() {
			return size;
		}

		public int getLines() {
			return lines;
		}

		@Override
		public void found(Search4JResult result) {
			size++;
			this.results.add(result);
		}

		@Override
		public void finished() {

		}

		@Override
		public void currentValue(int lineNum) {
			lines++;
		}
	};
}
