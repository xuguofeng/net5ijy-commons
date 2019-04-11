package org.net5ijy.commons.util.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

	public static final String MD5TYPE_32 = "32";

	public static final String MD5TYPE_16 = "16";

	public static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * MD5加密算法
	 * 
	 * @author 创建人：administrator
	 * @version 创建于：2018年11月16日 上午11:36:52
	 * @param text
	 *            - 需加密的文本
	 * @return
	 */
	public static String md5(String text) {
		return md5(text, null);
	}

	/**
	 * MD5加密算法
	 * 
	 * @param text
	 *            - 需加密的文本
	 * @param md5Type
	 *            - MD5TYPE_32/MD5TYPE_16
	 * @return 加密后的密文
	 */
	public static String md5(String text, String md5Type) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte b[] = md.digest();
			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			if (md5Type != null && md5Type.trim().equals(MD5TYPE_16)) {
				return buf.toString().substring(8, 24).toUpperCase();// 16位的加密
			}
			return buf.toString().toUpperCase();// 32位的加密
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * https://blog.csdn.net/xuxile/article/details/77963894 Java计算文件MD5值(支持大文件)
	 * 
	 * 获取文件md5
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getMD5(File file) throws IOException {

		try (FileInputStream in = new FileInputStream(file)) {

			MessageDigest MD5 = MessageDigest.getInstance("MD5");

			// 100MB缓冲区
			byte[] buffer = new byte[1024 * 1024 * 100];

			// 读取文件
			int len = in.read(buffer);

			while (len > -1) {
				MD5.update(buffer, 0, len);
				len = in.read(buffer);
			}

			return new String(encodeHex(MD5.digest(), DIGITS_LOWER));

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static char[] encodeHex(final byte[] data, final char[] toDigits) {
		final int l = data.length;
		final char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {

		String filename = "D:\\DownLoad\\4分钟看完世界文明发展史.mp4";

		long start1 = System.currentTimeMillis();

		String md5 = DigestUtils.md5Hex(new FileInputStream(filename));
		// String md5 = getMD5(new File(filename));

		System.out.println(System.currentTimeMillis() - start1);

		System.out.println(md5);
	}
}
