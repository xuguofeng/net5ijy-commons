package org.net5ijy.commons.util;
/*

	编译：

		$ javac -encoding utf-8 StringAndHex.java

	运行：

		$ java StringAndHex
		$ java StringAndHex str2hex
		$ java StringAndHex hex2str

		前两种方式效果相同，把用户输入的字符串转为16进制字符串
		第三个命令是把用户输入的16进制字符串转为原始字符串

		注意：使用的编码为UTF-8

*/

import java.util.Scanner;

public class StringAndHex {

	public static void main(String[] args) {

		System.out.println("\n欢迎使用, 退出请输入 quit\n");

		if (args == null || args.length == 0) {
			str2hex();
		} else if (args[0].equals("str2hex")) {
			str2hex();
		} else if (args[0].equals("hex2str")) {
			hex2str();
		} else {
			str2hex();
		}

		System.exit(0);
	}

	private static void str2hex() {

		Scanner scan = new Scanner(System.in);

		System.out.print("str2hex> ");

		String line = scan.nextLine();

		while (!"quit".equals(line)) {

			try {

				if (line != null && line.length() > 0) {

					byte[] buf = line.getBytes("utf-8");

					String hexString = bytes2HexString(buf);

					System.out.println(hexString);
				}

				System.out.print("str2hex> ");

				line = scan.nextLine();

			} catch (Exception e) {
				System.out.println("WARN: " + e.getMessage());
			}
		}
		scan.close();
	}

	private static void hex2str() {

		Scanner scan = new Scanner(System.in);

		System.out.print("hex2str> ");

		String line = scan.nextLine();

		while (!"quit".equals(line)) {

			try {

				if (line != null && line.length() > 0) {

					byte[] buf = hexString2Bytes(line);

					String str = new String(buf, "utf-8");

					System.out.println(str);
				}

				System.out.print("hex2str> ");

				line = scan.nextLine();

			} catch (Exception e) {
				System.out.println("WARN: " + e.getMessage());
			}
		}
		scan.close();
	}

	public static String bytes2HexString(byte[] buf) {

		StringBuilder sb = new StringBuilder();

		for (byte b : buf) {
			String hex = Integer.toHexString(b & 0xff);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex);
		}

		return sb.toString();
	}

	public static byte[] hexString2Bytes(String hex) {

		if (hex == null) {
			return null;
		}

		hex = hex.trim().toUpperCase();

		if (hex.equals("") || hex.length() % 2 != 0) {
			return null;
		}

		int len = hex.length() / 2;
		byte[] buf = new byte[len];
		char[] chs = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = 2 * i;
			buf[i] = (byte) (charToByte(chs[pos]) << 4 | charToByte(chs[pos + 1]));
		}
		return buf;
	}

	private static byte charToByte(char c) {
		byte b = (Integer.valueOf("0123456789ABCDEF".indexOf(c))).byteValue();
		return b;
	}
}
