package org.net5ijy.commons.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {

	public static void closeStream(Closeable stream) {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 从classpath或${user.dir}/config目录下加载配置文件输入流
	 * 
	 * @return
	 */
	public static InputStream resourceInputStream(String filename) {

		InputStream in = null;

		try {

			in = IOUtils.class.getClassLoader().getResourceAsStream(filename);

			if (in == null) {
				in = new FileInputStream(System.getProperty("user.dir")
						+ File.separator + "config" + File.separator + filename);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		return in;
	}
}
