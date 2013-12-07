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

import java.io.BufferedReader;
import java.io.File;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.search4j.model.Search4JCancellationEvent;
import com.search4j.model.Search4JModel;
import com.search4j.model.Search4JProgressCallback;
import com.search4j.model.Search4JResult;
import com.search4j.service.helper.BufferedReaderWithEncoding;

/**
 * 
 * @author Sergey Nechaev <serg.nechaev@gmail.com>
 * 
 */
public class Search4JFileSearch {

	private static final Log log = LogFactory.getLog(Search4JFileSearch.class);

	public void find(Search4JModel model, File file, String encoding, Search4JCancellationEvent cancellationEvent, Search4JProgressCallback progressCallback) {

		log.debug("File search: " + model);

		BufferedReader br = null;

		int lineNum = 0;

		try {

			br = BufferedReaderWithEncoding.createBufferedReader(file, encoding);

			String line = null;

			while ((line = br.readLine()) != null && (cancellationEvent == null || cancellationEvent.isNotCancelled())) {

				List<Search4JResult> finds = Search4JHelper.find(model, line);

				if (finds.isEmpty() == false) {

					for (Search4JResult find : finds) {

						find.setFile(file);

						find.setLineNum(lineNum);

						find.setText(line);

						if (progressCallback != null) {
							progressCallback.found(find);
						}

					}

					return;

				}

				lineNum++;

				if (progressCallback != null) {
					progressCallback.currentValue(lineNum);
				}
			}

		} catch (Exception e) {

			log.error("Error: " + file, e);

		} finally {

			IOUtils.closeQuietly(br);

			if (progressCallback != null) {
				progressCallback.finished();
			}

			if (progressCallback != null) {
				progressCallback.currentValue(lineNum);
			}

		}

	}
}
