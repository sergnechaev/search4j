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

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.search4j.Search4J;
import com.search4j.model.Search4JModel;
import com.search4j.model.Search4JResult;

public class TestFileTextSearch extends TestCase {

	public void testBasic() throws IOException {

		String read = IOUtils.toString(this.getClass().getResourceAsStream("/files/UTF-8.txt"), "UTF-8");

		String[] queries = { "storks", "because the jacket", "Eĥoŝanĝo ĉiuĵaŭde", "rvíztűr", "Thursda", "traditional", "Storks", "because the JACKET", "Eĥoŝanĝo ĉiUĵaŭde", "Rvíztűr",
				"thursda", "TRaditional" };

		for (String query : queries) {

			Search4JModel model = new Search4JModel(query, false, false, false);

			List<Search4JResult> find = new Search4J().find(model);

			assertNotNull(find);
			assertFalse(query + ": must be not empty: " + find.toString(), find.isEmpty());

			Search4JResult result = find.iterator().next();

			boolean e = StringUtils.equalsIgnoreCase(query, StringUtils.substring(read, result.getStart(), result.getEnd()));

			assertTrue(e);
		}

	}

	public void testMatchWholeWord() throws IOException {

		String read = IOUtils.toString(this.getClass().getResourceAsStream("/files/UTF-8.txt"), "UTF-8");

		String[] queries = { "stor", "because the jacke", "Eĥoŝanĝo ĉiuĵaŭd", "rvíztű", "Thursda", "traditiona" };

		for (String query : queries) {

			Search4JModel model = new Search4JModel(query, true, false, false);
			model.setText(read);

			List<Search4JResult> find = new Search4J().find(model);

			assertTrue(find.isEmpty());

		}

	}

	public void testMatchCase() throws IOException {

		String read = IOUtils.toString(this.getClass().getResourceAsStream("/files/UTF-8.txt"), "UTF-8");

		String[] queries = { "Storks", "because the JACKET", "Eĥoŝanĝo ĉiUĵaŭde", "Rvíztűr", "thursda", "TRaditional" };

		for (String query : queries) {

			Search4JModel model = new Search4JModel(query, false, true, false);
			model.setText(read);

			List<Search4JResult> find = new Search4J().find(model);

			assertNotNull(find);

			assertTrue(find.isEmpty());

		}
	}

}
