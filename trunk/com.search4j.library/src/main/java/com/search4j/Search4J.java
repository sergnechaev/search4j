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
package com.search4j;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import com.search4j.model.Search4JCancellationEvent;
import com.search4j.model.Search4JModel;
import com.search4j.model.Search4JProgressCallback;
import com.search4j.model.Search4JResult;
import com.search4j.service.Search4JFileSearch;
import com.search4j.service.Search4JHelper;

/**
 * 
 * @author Sergey.Nechaev <serg.nechaev@gmail.com>
 * 
 */
public class Search4J {

	/**
	 * Find occurrences in text.
	 */
	public List<Search4JResult> find(Search4JModel model, String text) {
		return Search4JHelper.find(model, text);
	}

	/**
	 * Find occurrences in a file using the specified encoding.
	 * 
	 * @param model
	 *            - the populated model. The "text" field will be ignored.
	 * @param file
	 *            - the file to search in.
	 * @param encoding
	 *            - the encoding of the file.
	 * @param cancellationEvent
	 *            - can be used to cancel the long-running search operation.
	 * @param progressCallback
	 *            - is used to inform the user about the statsus of the search
	 *            operation.
	 */
	public void find(Search4JModel model, File file, String encoding, Search4JCancellationEvent cancellationEvent, Search4JProgressCallback progressCallback) {
		new Search4JFileSearch().find(model, file, encoding, cancellationEvent, progressCallback);
	}

	/**
	 * Find occurrences in a file using the specified encoding.
	 * 
	 * @param model
	 *            - the populated model. The "text" field will be ignored.
	 * @param file
	 *            - the file to search in.
	 * @param recursively‎
	 *            whether to search in child subdirectories.
	 * @param encoding
	 *            - the encoding of the file.
	 * @param cancellationEvent
	 *            - can be used to cancel the long-running search operation.
	 * @param progressCallback
	 *            - is used to inform the user about the statsus of the search
	 *            operation.
	 */
	public void find(final Search4JModel model, final File folder, final String encoding, final boolean recursively‎, final Search4JCancellationEvent cancellationEvent,
			final Search4JProgressCallback progressCallback) {

		if (false == recursively‎) {

			for (File file : folder.listFiles()) {
				find(model, file, encoding, cancellationEvent, progressCallback);
			}

		} else {

			FileUtils.iterateFilesAndDirs(folder, new IOFileFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return true;
				}

				@Override
				public boolean accept(File file) {
 					find(model, file, encoding, cancellationEvent, progressCallback);
					return true;
				}
			}, new IOFileFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return true;
				}

				@Override
				public boolean accept(File file) {
					return true;
				}
			});
		}
	}

}
