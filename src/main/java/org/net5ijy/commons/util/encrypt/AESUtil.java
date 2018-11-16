package org.net5ijy.commons.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * http://www.cnblogs.com/dava/p/6416638.html<br />
 * JAVA加密算法系列-AesCBC<br />
 * <br />
 * 
 * http://www.cnblogs.com/dava/tag/%E5%8A%A0%E5%AF%86/<br />
 * JAVA加密算法系列-AES<br />
 * <br />
 * 
 * @author 创建人：xuguofeng
 * @version 创建于：2018年7月3日 下午3:37:34
 */
public class AESUtil {

	/**
	 * 加密
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年7月3日 下午3:43:45
	 * @param src
	 *            - 待加密字符串
	 * @param encoding
	 *            - 编码方式
	 * @param sKey
	 * @param ivParameter
	 * @return
	 */
	public static String encrypt(String src, String encoding, String sKey,
			String ivParameter) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = sKey.getBytes(encoding);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(
					ivParameter.getBytes(encoding));// 使用CBC模式，需要一个向量iv，可增加加密算法的强度

			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(src.getBytes(encoding));
			return new Base64().encodeAsString(encrypted);// 使用base64编码
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解密
	 * 
	 * @author 创建人：xuguofeng
	 * @version 创建于：2018年7月3日 下午3:44:41
	 * @param src
	 * @param encoding
	 * @param sKey
	 * @param ivParameter
	 * @return
	 */
	public static String decrypt(String src, String encoding, String sKey,
			String ivParameter) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = sKey.getBytes(encoding);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(
					ivParameter.getBytes(encoding));

			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] encrypted = new Base64().decode(src);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted);
			String originalString = new String(original, encoding);
			return originalString;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
