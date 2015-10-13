# How to detect encoding of a text file in Java #

Please, use this library to detect the encoding of the text file: https://code.google.com/p/juniversalchardet/

```
public class FileEncoding {

	private static final Log log = LogFactory.getLog(FileEncoding.class);

	public String detect(File file) {

		InputStream fis = null;

		String encoding = null;

		try {

			byte[] buf = new byte[4096 * 10];

			fis = new BufferedInputStream(new FileInputStream(file), buf.length);

			UniversalDetector detector = new UniversalDetector(null);

			int nread;
			int progress = 0;
			while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
				detector.handleData(buf, 0, nread);

				progress += nread;
				if (progress > ByteHelper.MB) {
					break;
				}
			}

			detector.dataEnd();

			encoding = detector.getDetectedCharset();

			if (encoding != null) {
				log.debug("Detected encoding = " + encoding);
			} else {
				log.debug("No encoding detected.");
			}

			detector.reset();

		} catch (Exception e) {
			log.error("Error: ", e);
		} finally {
			IOUtils.closeQuietly(fis);
		}

		return encoding;
	}

	public static void main(String[] a) {
		System.out.println(new FileEncoding().detect(new File("c:\\logs\\05-17_log.txt")));
	}
}

```