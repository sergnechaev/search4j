package com.search4j;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.search4j.model.Search4JModel;
import com.search4j.model.Search4JResult;

public class TestSimpleTextSearch extends TestCase {

	public void testBasic() throws IOException {

		String read = IOUtils.toString(this.getClass().getResourceAsStream("/files/UTF-8.txt"), "UTF-8");

		System.out.println(read);

		String[] queries = { "storks", "because the jacket", "Eĥoŝanĝo ĉiuĵaŭde", "rvíztűr", "Thursda", "traditional", "Storks", "because the JACKET",
				"Eĥoŝanĝo ĉiUĵaŭde", "Rvíztűr", "thursda", "TRaditional" };

		for (String query : queries) {

			Search4JModel model = new Search4JModel(query, false, false, false);
			model.setText(read);

			List<Search4JResult> find = new Search4J().find(model);

			assertNotNull(find);
			assertFalse(find.isEmpty());

			Search4JResult result = find.iterator().next();

			boolean e = StringUtils.equalsIgnoreCase(query, StringUtils.substring(read, result.getStart(), result.getEnd()));

			assertTrue(e);
		}

	}

	public void testMatchWholeWord() throws IOException {

		String read = IOUtils.toString(this.getClass().getResourceAsStream("/files/UTF-8.txt"), "UTF-8");

		System.out.println(read);

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

		System.out.println(read);

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
