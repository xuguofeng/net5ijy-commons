package org.net5ijy.commons.util.security;

public class NoOperationPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(String password) {
		return password;
	}

	@Override
	public boolean match(String password, String encryptPasswd) {
		return password != null && password.equals(encryptPasswd);
	}
}
