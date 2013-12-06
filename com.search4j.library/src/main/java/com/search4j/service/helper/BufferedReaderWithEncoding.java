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
package com.search4j.service.helper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Sergey Nechaev <serg.nechaev@gmail.com>
 * 
 */
public class BufferedReaderWithEncoding {

	private static final Log log = LogFactory.getLog(BufferedReaderWithEncoding.class);

	private static final int BUFFER_SIZE = 200 * 1024;

	/**
	 * Create BufferedReader with the specified encoding.
	 */
	public static BufferedReader createBufferedReader(File file, String enc) {

		BufferedReader reader = null;

		try {

			if (enc == null) {
				reader = new BufferedReader(new FileReader(file), BUFFER_SIZE);
			} else {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), enc), BUFFER_SIZE);
			}

		} catch (Exception e) {
			log.error("Error: ", e);
		}

		return reader;
	}

	/**
	 * Create InputStreamReader with the specified encoding
	 */
	public static InputStreamReader createInputStreamReader(File file, String encoding) throws Exception {

		InputStreamReader reader = null;

		FileInputStream fis = new FileInputStream(file);

		if (encoding == null) {
			reader = new InputStreamReader(new BufferedInputStream(fis));
		} else {
			reader = new InputStreamReader(new BufferedInputStream(fis), encoding);
		}
		return reader;
	}

}
