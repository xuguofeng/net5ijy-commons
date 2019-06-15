package org.net5ijy.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.TreeSet;

public class FileCombiner {

	public static void main(String[] args) throws Exception {

		String dirStr = "C:\\Users\\XGF\\Documents\\Tencent Files\\572399495\\FileRecv\\MobileFile\\xx";
		String finalName = "C:\\Users\\XGF\\Documents\\Tencent Files\\572399495\\FileRecv\\MobileFile\\xx.ts";

		File dir = new File(dirStr);

		String[] list = dir.list();

		TreeSet<String> fileNameSet = new TreeSet<String>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						if (o1.length() != o2.length()) {
							return o1.length() - o2.length();
						}
						return o1.compareTo(o2);
					}
				});

		for (String s : list) {
			if (s.endsWith(".ts")) {
				fileNameSet.add(s);
			}
		}

		OutputStream out = new FileOutputStream(finalName);

		for (String fileName : fileNameSet) {
			InputStream in = new FileInputStream(dirStr + "\\" + fileName);
			byte[] buf = new byte[1024 * 16];
			int len = in.read(buf);
			while (len > 0) {
				out.write(buf, 0, len);
				len = in.read(buf);
			}
			in.close();
		}
		out.close();
	}
}
