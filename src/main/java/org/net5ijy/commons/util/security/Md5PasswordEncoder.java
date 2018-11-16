package org.net5ijy.commons.util.security;

import static org.net5ijy.commons.util.encrypt.MD5Util.*;

public class Md5PasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(String password) {
		return md5(password);
	}

	@Override
	public boolean match(String password, String encryptPasswd) {
		return md5(password).equals(encryptPasswd);
	}
}
