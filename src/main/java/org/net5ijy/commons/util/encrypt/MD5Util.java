package org.net5ijy.commons.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static final String MD5TYPE_32 = "32";

	public static final String MD5TYPE_16 = "16";

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
}
