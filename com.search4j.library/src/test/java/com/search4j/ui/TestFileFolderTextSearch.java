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

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

import com.search4j.Search4J;
import com.search4j.model.Search4JCancellationEvent;
import com.search4j.model.Search4JModel;
import com.search4j.model.Search4JProgressCallback;
import com.search4j.model.Search4JResult;

public class TestFileFolderTextSearch extends TestCase {

	public void testBasic() throws IOException, URISyntaxException {

		String[] queries = { "storks", "because the jacket", "Eĥoŝanĝo ĉiuĵaŭde", "rvíztűr", "Thursda", "traditional", "Storks", "because the JACKET", "Eĥoŝanĝo ĉiUĵaŭde", "Rvíztűr", "thursda",
				"TRaditional" };

		for (String query : queries) {

			Search4JModel model = new Search4JModel(query, false, false, false);

			SPC spc = new SPC();

			new Search4J().find(model, new File("D:/oss/com.search4j.library/src/test/resources/files/11/"), "UTF-8", false, new Search4JCancellationEvent(), spc);

			Search4JResult result = spc.getResult();

			boolean e = StringUtils.equalsIgnoreCase(query, StringUtils.substring(spc.getResult().getText(), result.getStart(), result.getEnd()));

			assertTrue(e);
		}

	}

	public void testMatchWholeWord() throws IOException, URISyntaxException {

		String[] queries = { "stor", "because the jacke", "Eĥoŝanĝo ĉiuĵaŭd", "rvíztű", "Thursda", "traditiona" };

		for (String query : queries) {

			Search4JModel model = new Search4JModel(query, true, false, false);

			SPC spc = new SPC();

			new Search4J().find(model, new File("D:/oss/com.search4j.library/src/test/resources/files/11/"), "UTF-8", false, new Search4JCancellationEvent(), spc);

			assertTrue(spc.getResult() == null);

		}

	}

	public void testMatchCase() throws IOException, URISyntaxException {

		String[] queries = { "Storks", "because the JACKET", "Eĥoŝanĝo ĉiUĵaŭde", "Rvíztűr", "thursda", "TRaditional" };

		for (String query : queries) {

			Search4JModel model = new Search4JModel(query, false, true, false);

			SPC spc = new SPC();

			new Search4J().find(model, new File("D:/oss/com.search4j.library/src/test/resources/files/11/"), "UTF-8", false, new Search4JCancellationEvent(), spc);

			Search4JResult result = spc.getResult();

			assertNull(result);
		}
	}

	private static class SPC implements Search4JProgressCallback {

		int size;

		int lines;

		Search4JResult result;

		public Search4JResult getResult() {
			return result;
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
			this.result = result;
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
