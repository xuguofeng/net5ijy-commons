package org.net5ijy.commons.util.security;

import static org.net5ijy.commons.util.encrypt.AESUtil.*;
import static org.net5ijy.commons.util.encrypt.MD5Util.*;

public class SaltTimestampPasswordEncoder implements PasswordEncoder {

	private static final String SALT = "net5ijy.org";

	private static final String S_KEY = "1234567890123456";

	private static final String IV_PARAMETER = "abcdefghijklmnop";

	@Override
	public String encode(String password) {
		// 对(原密码 + salt) 进行md5加密
		String md5PwdWithSalt = md5(password + SALT);
		// 对 拼接了时间戳的字符串 做AES加密
		return encrypt(md5PwdWithSalt + "_" + System.currentTimeMillis(),
				"UTF-8", S_KEY, IV_PARAMETER);
	}

	@Override
	public boolean match(String password, String encryptPasswd) {
		// 对 (原密码 + salt) 进行md5加密
		String md5PwdWithSalt = md5(password + SALT);

		// 对 加密密码 进行AES反解
		String md5WithTime = decrypt(encryptPasswd, "UTF-8", S_KEY,
				IV_PARAMETER);

		// 获取 加密密码的 (原密码 + salt) 子串
		String md5PwdWithSalt2 = md5WithTime.replaceFirst("_\\d+$", "");

		return md5PwdWithSalt.equals(md5PwdWithSalt2);
	}
}
