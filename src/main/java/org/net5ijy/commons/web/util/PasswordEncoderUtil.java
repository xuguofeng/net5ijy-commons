package org.net5ijy.commons.web.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * org.net5ijy.springcloud.admin.service.util.PasswordEncoderUtil 类
 *
 * @author xuguofeng
 * @date 2019/5/29 10:04
 */
public class PasswordEncoderUtil {

	public static String bCryptPasswordEncode(String password) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
}
